package myuni.JwtModels;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private String email;
}
