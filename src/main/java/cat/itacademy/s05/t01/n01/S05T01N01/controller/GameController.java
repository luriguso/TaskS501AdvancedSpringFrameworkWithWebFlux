package cat.itacademy.s05.t01.n01.S05T01N01.controller;

import cat.itacademy.s05.t01.n01.S05T01N01.dto.request.CreateGameRequest;
import cat.itacademy.s05.t01.n01.S05T01N01.dto.request.Move;
import cat.itacademy.s05.t01.n01.S05T01N01.dto.response.GameResponse;
import cat.itacademy.s05.t01.n01.S05T01N01.service.GameService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new")
    public Mono<GameResponse> createGame(@RequestBody CreateGameRequest request) {
        return gameService.createGame(request.getPlayerName());
    }

    @GetMapping("/{gameId}")
    public Mono<GameResponse> getGame(@PathVariable String gameId) {
        return gameService.getGame(gameId);
    }

    @PostMapping("/{gameId}/play")
    public Mono<GameResponse> playMove(@PathVariable String gameId, @RequestBody Move move) {
        return gameService.playMove(gameId, move);
    }

    @DeleteMapping("/{gameId}/delete")
    public Mono<Void> deleteGame(@PathVariable String gameId) {
        return gameService.deleteGame(gameId);
    }

    @GetMapping("/player/{playerName}")
    public Flux<GameResponse> getGamesByPlayer(@PathVariable String playerName) {
        return gameService.getGamesByPlayer(playerName);
    }
}
