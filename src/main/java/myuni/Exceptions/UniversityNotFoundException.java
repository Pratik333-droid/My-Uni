package myuni.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UniversityNotFoundException extends RuntimeException{
    private String message;
}
