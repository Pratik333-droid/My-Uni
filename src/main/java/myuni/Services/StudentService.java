package myuni.Services;

import myuni.DTOs.StudentDtoInput;
import myuni.DTOs.StudentDtoOutput;

import java.util.List;

public interface StudentService {
    public List<StudentDtoOutput> getStudents();
    public StudentDtoOutput addStudent(StudentDtoInput newStudent);
    public StudentDtoOutput getStudentById(String id);
}
