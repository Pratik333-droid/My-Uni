package myuni.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myuni.DTOs.StudentDtoInput;
import myuni.DTOs.StudentDtoOutput;
import myuni.Services.Impl.StudentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;
    private final PasswordEncoder passwordEncoder;

    // get all students
    @GetMapping
    public ResponseEntity<List<StudentDtoOutput>> getStudents(){
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    // return the currently logged student
    @GetMapping(path = "/current")
    public String getCurrentLoggedInStudent(Principal principal){
        return principal.toString();
    }

    // add a student
    @PostMapping
    public ResponseEntity<StudentDtoOutput> addStudent(@RequestBody @Valid StudentDtoInput newStudent){

        String plainPassword = newStudent.getPassword();
        String hashedPassword = passwordEncoder.encode(plainPassword);
        newStudent.setPassword(hashedPassword);
        StudentDtoOutput responseDTO = studentService.addStudent(newStudent);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    // get a student with a particular id
    @GetMapping(path = "/{id}")
    public ResponseEntity<StudentDtoOutput> getStudentById(@PathVariable String id){
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

}

