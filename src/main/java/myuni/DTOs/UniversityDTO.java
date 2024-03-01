package myuni.DTOs;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UniversityDTO {
    private String id;
    @NotBlank(message = "The name field can't be blank")
    private String name;

    @NotBlank(message = "The location field can't be blank")
    private String location;

    @Min(value = 1, message = "University rank can't be less than 1")
    @Max(value = 24000, message = "University Rank can't be greater than 24000")
    @NotNull(message = "The global rank field can't be blank")
    private int globalRank;
}
