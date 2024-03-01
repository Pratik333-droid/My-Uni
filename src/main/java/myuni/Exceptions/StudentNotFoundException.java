package myuni.Exceptions;

import lombok.*;

//@AllArgsConstructor
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentNotFoundException extends RuntimeException {
    private String message;
}
