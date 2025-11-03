package cat.itacademy.s05.t01.n01.S05T01N01.service;

import cat.itacademy.s05.t01.n01.S05T01N01.domain.sql.Player;
import cat.itacademy.s05.t01.n01.S05T01N01.dto.response.Ranking;
import cat.itacademy.s05.t01.n01.S05T01N01.exception.AttributeInvalidException;
import cat.itacademy.s05.t01.n01.S05T01N01.exception.DuplicatePlayerException;
import cat.itacademy.s05.t01.n01.S05T01N01.exception.EntityNotFoundException;
import cat.itacademy.s05.t01.n01.S05T01N01.repository.sql.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> createPlayer(String name) {

        return Mono.fromCallable(() -> {
            if(name.isEmpty()){
                throw new AttributeInvalidException("Error: El nombre no puede ser vacio");
            }
            Optional<Player> playerDB = playerRepository.findByName(name);
            if (playerDB.isPresent()) {
                throw new DuplicatePlayerException("Error: Ya existe un jugador llamado " + name);
            }
            Player player = new Player();
            player.setName(name);
            player.setGamesPlayed(0);
            player.setGamesWon(0);
            return playerRepository.save(player);
        });
    }

    public Mono<Player> updatePlayerName(Long playerId, String newName) {
        return Mono.fromCallable(() ->
                playerRepository.findById(playerId)
                        .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"))
        ).flatMap(player -> Mono.fromCallable(() -> {
            player.setName(newName);
            return playerRepository.save(player);
        }));
    }

    public Mono<Player> getPlayer(Long playerId) {
        return Mono.fromCallable(() ->
                playerRepository.findById(playerId)
                        .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"))
        );
    }

    public Mono<Void> deletePlayer(Long playerId) {
        return Mono.fromCallable(() -> {
            Player player = playerRepository.findById(playerId)
                    .orElseThrow(() -> new EntityNotFoundException("Jugador no encontrado"));
            playerRepository.deleteById(playerId);
            return Void.TYPE;
        }).then();
    }


    public Flux<Ranking> getRanking() {
        return Flux.fromIterable(playerRepository.findAllByOrderByGamesWonDesc())
                .map(player -> Ranking.builder()
                        .playerId(player.getId())
                        .playerName(player.getName())
                        .winRate(player.getGamesPlayed() == 0 ? 0.0 :
                                (double) player.getGamesWon() / player.getGamesPlayed())
                        .totalGames(player.getGamesPlayed())
                        .build());
    }
}
