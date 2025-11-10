package cat.itacademy.s05.t01.n01.S05T01N01.player;

import cat.itacademy.s05.t01.n01.S05T01N01.domain.sql.Player;
import cat.itacademy.s05.t01.n01.S05T01N01.exception.DuplicatePlayerException;
import cat.itacademy.s05.t01.n01.S05T01N01.repository.sql.PlayerRepository;
import cat.itacademy.s05.t01.n01.S05T01N01.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void testCreatePlayer_Success() {
        Player newPlayer = new Player(null, "Luis", 0, 0);

        when(playerRepository.findByName("Luis")).thenReturn(Mono.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(newPlayer));

        StepVerifier.create(playerService.createPlayer("Luis"))
                .expectNextMatches(player -> player.getName().equals("Luis"))
                .verifyComplete();
    }

    @Test
    void testCreatePlayer_Duplicate() {
        Player existing = new Player(1L, "Luis", 10, 5);
        when(playerRepository.findByName("Luis")).thenReturn(Mono.just(existing));

        StepVerifier.create(playerService.createPlayer("Luis"))
                .expectError(DuplicatePlayerException.class)
                .verify();
    }

    @Test
    void testCreatePlayer_InvalidName() {
        StepVerifier.create(playerService.createPlayer(""))
                .expectErrorMatches(e -> e instanceof cat.itacademy.s05.t01.n01.S05T01N01.exception.AttributeInvalidException)
                .verify();
    }

}