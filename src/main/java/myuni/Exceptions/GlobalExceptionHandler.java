package myuni.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(StudentNotFoundException.class)
        public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException exp){
        logger.error(exp.getMessage());
        return new ResponseEntity<>(exp.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomExpiredJwtException.class)
        public ResponseEntity<String> handleCustomExpiredJwtException(CustomExpiredJwtException exp){
        logger.error(exp.getMessage());
        return new ResponseEntity<>(exp.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MiscException.class)
        public ResponseEntity<String> handleMiscException(MiscException exp){
        logger.error(exp.getMessage());
        return new ResponseEntity<>(exp.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomTransactionSystemException.class)
        public ResponseEntity<String> handleTransactionSystemException(CustomTransactionSystemException exp){
        logger.error(exp.getMessage());
        return new ResponseEntity<>(exp.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArguments(MethodArgumentNotValidException exp){
        Map<String, String> errorMap = new HashMap<>();
        exp.getBindingResult().getFieldErrors().forEach(error ->{
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
