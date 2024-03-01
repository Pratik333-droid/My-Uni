package myuni.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDtoInput {
    private String id;

    @NotBlank(message = "name can't be blank")
    private String name;

    @NotBlank(message = "email field can't be blank")
    @Email(message = "email already exists or is invalid")
    private String email;

    @NotBlank(message = "password can't be blank")
    @Size(min = 5, message = "password must be of minimum length 5")
    // can include a regular expression to validate pw here
    private String password;
}