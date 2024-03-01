package myuni.Services.Impl;

import lombok.RequiredArgsConstructor;
import myuni.DAOs.StudentRepository;
import myuni.DTOs.StudentDtoInput;
import myuni.DTOs.StudentDtoOutput;
import myuni.Entities.Student;
import myuni.Exceptions.CustomTransactionSystemException;
import myuni.Exceptions.MiscException;
import myuni.Exceptions.StudentNotFoundException;
import myuni.Mappers.IMapper;
import myuni.Services.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final IMapper iMapper;
    @Override
    public List<StudentDtoOutput> getStudents(){
        return studentRepository.findAll().stream().map(iMapper::studentEntityToDtoOutput).collect(Collectors.toList());
    }

    @Override
    public StudentDtoOutput addStudent(StudentDtoInput newStudentDtoInput) {
        try {
            Student newStudentEntity = iMapper.studentDtoToEntity(newStudentDtoInput);
            Student response = studentRepository.save(newStudentEntity);
            return iMapper.studentEntityToDtoOutput(response);
        } catch (TransactionSystemException exp) {
            System.out.println("exp name = "+exp.getClass());
            throw new CustomTransactionSystemException(exp.getMessage());
        } catch (Exception exp) {
            System.out.println("exp name = "+exp.getClass());
            throw new MiscException(exp.getMessage());
        }
    }

    @Override
    public StudentDtoOutput getStudentById(String id) {

        // handle an exception here
        Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException("Student with the provided id does not exist."));
        return iMapper.studentEntityToDtoOutput(student);

    }



}
