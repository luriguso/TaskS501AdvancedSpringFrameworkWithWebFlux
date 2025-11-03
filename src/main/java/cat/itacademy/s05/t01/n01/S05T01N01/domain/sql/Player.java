package cat.itacademy.s05.t01.n01.S05T01N01.domain.sql;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int gamesPlayed;
    private int gamesWon;

}
