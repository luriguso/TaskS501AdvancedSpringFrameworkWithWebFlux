package cat.itacademy.s05.t01.n01.S05T01N01.repository.sql;

import cat.itacademy.s05.t01.n01.S05T01N01.domain.sql.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String name);
    List<Player> findAllByOrderByGamesWonDesc();
}