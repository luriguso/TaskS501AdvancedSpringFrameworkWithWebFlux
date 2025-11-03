package cat.itacademy.s05.t01.n01.S05T01N01.player;

import cat.itacademy.s05.t01.n01.S05T01N01.domain.sql.Player;
import cat.itacademy.s05.t01.n01.S05T01N01.repository.sql.PlayerRepository;
import cat.itacademy.s05.t01.n01.S05T01N01.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerServiceTest {
    private PlayerRepository playerRepository;
    private PlayerService playerService;

    @BeforeEach
    public void setUp(){
        playerRepository = Mockito.mock(PlayerRepository.class);
        playerService = new PlayerService(playerRepository);
    }

    @Test
    void createPlayer_shouldReturnPlayerWithCorrectName() {
        String playerName = "Luri";
        Player player = new Player(1L, playerName, 0, 0);

        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Mono<Player> result = playerService.createPlayer(playerName);

        Player savedPlayer = result.block();
        assertAll(
                ()-> assertNotNull(savedPlayer),
                ()-> assertEquals(playerName, savedPlayer.getName()),
                ()-> assertEquals(0, savedPlayer.getGamesPlayed()),
                ()-> assertEquals(0, savedPlayer.getGamesWon())
        );

        verify(playerRepository, times(1)).save(any(Player.class));
    }
}
