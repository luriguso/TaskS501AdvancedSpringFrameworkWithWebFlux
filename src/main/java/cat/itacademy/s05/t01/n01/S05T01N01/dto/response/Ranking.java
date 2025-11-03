package cat.itacademy.s05.t01.n01.S05T01N01.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ranking {
    private Long playerId;
    private String playerName;
    private Double winRate;
    private Integer totalGames;
}