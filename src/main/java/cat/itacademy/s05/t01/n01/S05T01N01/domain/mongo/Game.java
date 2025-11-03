package cat.itacademy.s05.t01.n01.S05T01N01.domain.mongo;

import cat.itacademy.s05.t01.n01.S05T01N01.dto.request.Move;
import cat.itacademy.s05.t01.n01.S05T01N01.utils.Deck;
import cat.itacademy.s05.t01.n01.S05T01N01.utils.GameState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Document(collection = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id
    private String id;

    private String playerId;
    private String playerName;
    private GameState state;

    private Deck deck;
    private List<Card> playerHand = new ArrayList<>();
    private List<Card> dealerHand = new ArrayList<>();

    private List<Move> moves;

    private Instant createdAt;
    private Instant updatedAt;
}
