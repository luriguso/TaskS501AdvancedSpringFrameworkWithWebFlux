package cat.itacademy.s05.t01.n01.S05T01N01.player;

import cat.itacademy.s05.t01.n01.S05T01N01.controller.PlayerController;
import cat.itacademy.s05.t01.n01.S05T01N01.domain.sql.Player;
import cat.itacademy.s05.t01.n01.S05T01N01.dto.request.CreatePlayerRequest;
import cat.itacademy.s05.t01.n01.S05T01N01.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerControllerTest {

    private PlayerService playerService;
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        playerService = Mockito.mock(PlayerService.class);
        playerController = new PlayerController(playerService);
    }

    @Test
    void createPlayer_shouldCallServiceAndReturnPlayer() {
        String name = "Luis";
        Player mockPlayer = new Player(1L, name, 0, 0);
        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest();
        createPlayerRequest.setName(name);

        when(playerService.createPlayer(name)).thenReturn(Mono.just(mockPlayer));

        Mono<Player> result = playerController.createPlayer(createPlayerRequest);

        StepVerifier.create(result)
                .expectNextMatches(player -> player.getName().equals(name)
                        && player.getGamesPlayed() == 0
                        && player.getGamesWon() == 0)
                .verifyComplete();

        verify(playerService, times(1)).createPlayer(name);
    }

}
