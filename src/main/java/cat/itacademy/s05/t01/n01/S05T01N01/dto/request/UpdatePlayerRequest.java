package cat.itacademy.s05.t01.n01.S05T01N01.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePlayerRequest {
    private String name;
}