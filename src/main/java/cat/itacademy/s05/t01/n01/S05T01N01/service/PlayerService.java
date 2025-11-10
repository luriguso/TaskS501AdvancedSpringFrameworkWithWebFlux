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

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> createPlayer(String name) {
        if (name == null || name.isBlank()) {
            return Mono.error(new AttributeInvalidException("Error: El nombre no puede ser vacío"));
        }
        return playerRepository.findByName(name)
                .flatMap(p -> Mono.error(new DuplicatePlayerException("Error: Ya existe un jugador llamado " + name)))
                .switchIfEmpty(
                        playerRepository.save(Player.builder()
                                .name(name.trim())
                                .gamesPlayed(0)
                                .gamesWon(0)
                                .build()
                        )
                )
                .cast(Player.class);
    }

    public Mono<Player> updatePlayerName(Long playerId, String newName) {
        if (newName == null || newName.isBlank()) {
            return Mono.error(new AttributeInvalidException("Error: El nombre no puede ser vacío"));
        }
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Jugador no encontrado")))
                .flatMap(p -> {
                    p.setName(newName.trim());
                    return playerRepository.save(p);
                });
    }

    public Mono<Player> getPlayer(Long playerId) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Jugador no encontrado")));
    }

    public Mono<Void> deletePlayer(Long playerId) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Jugador no encontrado")))
                .flatMap(existing -> playerRepository.deleteById(playerId));
    }

    public Flux<Ranking> getRanking() {
        return playerRepository.findAllByOrderByGamesWonDesc()
                .map(player -> Ranking.builder()
                        .playerId(player.getId())
                        .playerName(player.getName())
                        .winRate(player.getGamesPlayed() == 0 ? 0.0 :
                                (double) player.getGamesWon() / player.getGamesPlayed())
                        .totalGames(player.getGamesPlayed())
                        .build());
    }
}
