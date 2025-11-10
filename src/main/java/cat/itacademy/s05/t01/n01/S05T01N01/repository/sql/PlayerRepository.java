package cat.itacademy.s05.t01.n01.S05T01N01.repository.sql;

import cat.itacademy.s05.t01.n01.S05T01N01.domain.sql.Player;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Long> {
    Mono<Player> findByName(String name);
    Flux<Player> findAllByOrderByGamesWonDesc();
}
