package cat.itacademy.s05.t01.n01.S05T01N01.dto.response;

import cat.itacademy.s05.t01.n01.S05T01N01.domain.mongo.Card;
import cat.itacademy.s05.t01.n01.S05T01N01.utils.GameState;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameResponse {
    private String id;
    private String playerName;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private GameState state;
}