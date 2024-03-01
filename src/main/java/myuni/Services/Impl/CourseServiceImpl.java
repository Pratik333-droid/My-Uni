package myuni.Services.Impl;

import lombok.RequiredArgsConstructor;
import myuni.DTOs.CourseDTO;
import myuni.Entities.Course;
import myuni.DAOs.CourseRepository;
import myuni.Exceptions.CourseNotFoundException;
import myuni.Mappers.IMapper;
import myuni.Services.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
//    @NonNull
    private final IMapper imapper;
    @Override
    public List<CourseDTO> getCourses() {
        return (courseRepository.findAll().stream().map(imapper::courseEntityToDto).collect(Collectors.toList()));
    }

    @Override
    public CourseDTO addCourse(CourseDTO courseDTO) {
//        Course course = mapper.convertToEntity(courseDTO);
        Course course = imapper.courseDtoToEntity(courseDTO);
        Course response = courseRepository.save(course);
        return imapper.courseEntityToDto(response);
    }

    @Override
    public CourseDTO getCourseById(String id) {
        return imapper.courseEntityToDto(courseRepository.findById(id).orElseThrow(()-> new CourseNotFoundException("The course with given id doesn't exist.")));
    }

    @Override
    public void deleteCourseById(String id){
        courseRepository.deleteById(id);
    }

    @Override
    public boolean checkIfCourseExists(String id) {
        return courseRepository.existsById(id);
    }

}
