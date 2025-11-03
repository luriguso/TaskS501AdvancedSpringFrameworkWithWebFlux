package cat.itacademy.s05.t01.n01.S05T01N01.controller;

import cat.itacademy.s05.t01.n01.S05T01N01.domain.sql.Player;
import cat.itacademy.s05.t01.n01.S05T01N01.dto.request.CreatePlayerRequest;
import cat.itacademy.s05.t01.n01.S05T01N01.dto.request.UpdatePlayerRequest;
import cat.itacademy.s05.t01.n01.S05T01N01.dto.response.Ranking;
import cat.itacademy.s05.t01.n01.S05T01N01.service.PlayerService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/new")
    public Mono<Player> createPlayer(@RequestBody CreatePlayerRequest request) {
        return playerService.createPlayer(request.getName());
    }

    @PutMapping("/{playerId}")
    public Mono<Player> updatePlayerName(@PathVariable Long playerId, @RequestBody UpdatePlayerRequest request) {
        return playerService.updatePlayerName(playerId, request.getName());
    }

    @GetMapping("/{playerId}")
    public Mono<Player> getPlayer(@PathVariable Long playerId) {
        return playerService.getPlayer(playerId);
    }

    @DeleteMapping("/{playerId}/delete")
    public Mono<Void> deletePlayer(@PathVariable Long playerId) {
        return playerService.deletePlayer(playerId);
    }

    @GetMapping("/ranking")
    public Flux<Ranking> getRanking() {
        return playerService.getRanking();
    }
}
