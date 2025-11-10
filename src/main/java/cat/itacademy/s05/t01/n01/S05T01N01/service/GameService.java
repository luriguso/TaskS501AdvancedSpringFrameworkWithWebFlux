package cat.itacademy.s05.t01.n01.S05T01N01.service;

import cat.itacademy.s05.t01.n01.S05T01N01.domain.mongo.Card;
import cat.itacademy.s05.t01.n01.S05T01N01.domain.mongo.Game;
import cat.itacademy.s05.t01.n01.S05T01N01.dto.request.Move;
import cat.itacademy.s05.t01.n01.S05T01N01.dto.response.GameResponse;
import cat.itacademy.s05.t01.n01.S05T01N01.exception.AttributeInvalidException;
import cat.itacademy.s05.t01.n01.S05T01N01.exception.EntityNotFoundException;
import cat.itacademy.s05.t01.n01.S05T01N01.exception.GameAlreadyFinishedException;
import cat.itacademy.s05.t01.n01.S05T01N01.exception.InvalidMoveException;
import cat.itacademy.s05.t01.n01.S05T01N01.repository.mongo.GameRepository;
import cat.itacademy.s05.t01.n01.S05T01N01.repository.sql.PlayerRepository;
import cat.itacademy.s05.t01.n01.S05T01N01.utils.Deck;
import cat.itacademy.s05.t01.n01.S05T01N01.utils.GameState;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public GameService(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    public Mono<GameResponse> createGame(String playerName) {
        if (playerName == null || playerName.isBlank()) {
            return Mono.error(new AttributeInvalidException("Error: El nombre no puede ser vacío"));
        }

        return playerRepository.findByName(playerName)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Jugador no encontrado")))
                .flatMap(player -> {
                    Deck deck = Deck.multipleDecks(2);
                    List<Card> cards = new ArrayList<>(deck.getCards());

                    List<Card> playerHand = new ArrayList<>();
                    List<Card> dealerHand = new ArrayList<>();

                    playerHand.add(cards.remove(0));
                    dealerHand.add(cards.remove(0));
                    playerHand.add(cards.remove(0));
                    dealerHand.add(cards.remove(0));

                    Game game = new Game();
                    game.setPlayerName(player.getName());
                    game.setDeck(new Deck(cards));
                    game.setPlayerHand(playerHand);
                    game.setDealerHand(dealerHand);
                    game.setState(GameState.IN_PROGRESS);

                    return gameRepository.save(game);
                })
                .map(this::toGameResponse);
    }




    public Mono<GameResponse> getGame(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Partida no encontrada")))
                .map(this::toGameResponse);
    }

    public Mono<GameResponse> playMove(String gameId, Move move) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Partida no encontrada")))
                .flatMap(game -> {
                    if (game.getState() == GameState.PLAYER_WON ||
                            game.getState() == GameState.DEALER_WON ||
                            game.getState() == GameState.TIE) {
                        return Mono.error(new GameAlreadyFinishedException("El juego ya ha terminado"));
                    }

                    if (game.getPlayerHand() == null) game.setPlayerHand(new ArrayList<>());
                    if (game.getDealerHand() == null) game.setDealerHand(new ArrayList<>());

                    List<Card> playerHand = game.getPlayerHand();
                    List<Card> dealerHand = game.getDealerHand();

                    if (game.getMoves() == null) game.setMoves(new ArrayList<>());
                    game.getMoves().add(move);

                    switch (move.getMoveType()) {
                        case HIT -> {
                            if (!game.getDeck().getCards().isEmpty()) {
                                playerHand.add(game.getDeck().getCards().remove(0));
                            }
                            if (calculateHandValue(playerHand) > 21) {
                                game.setState(GameState.DEALER_WON);
                            } else {
                                game.setState(GameState.IN_PROGRESS);
                            }
                        }
                        case STAND -> {
                            while (calculateHandValue(dealerHand) < 17 && !game.getDeck().getCards().isEmpty()) {
                                dealerHand.add(game.getDeck().getCards().remove(0));
                            }

                            int playerTotal = calculateHandValue(playerHand);
                            int dealerTotal = calculateHandValue(dealerHand);

                            if (playerTotal > 21) {
                                game.setState(GameState.DEALER_WON);
                            } else if (dealerTotal > 21) {
                                game.setState(GameState.PLAYER_WON);
                            } else if (playerTotal > dealerTotal) {
                                game.setState(GameState.PLAYER_WON);
                            } else if (playerTotal < dealerTotal) {
                                game.setState(GameState.DEALER_WON);
                            } else {
                                game.setState(GameState.TIE);
                            }
                        }
                        default -> {
                            return Mono.error(new InvalidMoveException("Movimiento no soportado"));
                        }
                    }

                    game.setPlayerHand(playerHand);
                    game.setDealerHand(dealerHand);

                    return gameRepository.save(game);
                })
                .map(this::toGameResponse);
    }

    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Partida no encontrada")))
                .flatMap(game -> gameRepository.deleteById(gameId));
    }



    public Flux<GameResponse> getGamesByPlayer(String playerName) {
        return gameRepository.findByPlayerName(playerName)
                .map(this::toGameResponse);
    }

    private GameResponse toGameResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .playerName(game.getPlayerName())
                .playerHand(game.getPlayerHand())
                .dealerHand(game.getDealerHand())
                .state(game.getState())
                .build();
    }

    private int calculateHandValue(List<Card> hand) {
        int total = 0;
        int aces = 0;

        for (Card card : hand) {
            total += card.getValue();
            if ("A".equals(card.getRank())) aces++;
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

}
