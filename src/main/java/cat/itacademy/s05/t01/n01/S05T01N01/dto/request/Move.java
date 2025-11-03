package cat.itacademy.s05.t01.n01.S05T01N01.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {
    private MoveType moveType;
    private Double bet;
}