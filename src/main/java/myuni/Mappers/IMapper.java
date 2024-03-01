package myuni.Mappers;

import myuni.DTOs.CourseDTO;
import myuni.DTOs.StudentDtoInput;
import myuni.DTOs.StudentDtoOutput;
import myuni.DTOs.UniversityDTO;
import myuni.Entities.Course;
import myuni.Entities.Student;
import myuni.Entities.University;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface IMapper {
    Course courseDtoToEntity(CourseDTO courseDTO);
    CourseDTO courseEntityToDto(Course course);

    Student studentDtoToEntity(StudentDtoInput studentDtoInput);
    StudentDtoInput studentEntityToDtoInput(Student student);
    StudentDtoOutput studentEntityToDtoOutput(Student student);

    UniversityDTO universityEntityToDTO(University university);
    University universityDtoToEntity(UniversityDTO universityDTO);
}
