package myuni.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDTO {
    private String id;

    @NotBlank(message = "name can't be blank")
    private String name;

    @NotNull(message = "credit hours field can't be null")
    private int creditHr;
}
