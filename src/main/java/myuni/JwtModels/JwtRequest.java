package myuni.JwtModels;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class JwtRequest {
//    private String id;
    private String email;
    private String password;
}
