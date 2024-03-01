package myuni.Services;

import myuni.DTOs.CourseDTO;
import myuni.Entities.Course;
import myuni.DAOs.CourseRepository;
import myuni.Mappers.IMapper;
import myuni.Services.Impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepositoryMock;

    @Mock
    private IMapper iMapper;
    @InjectMocks
    private CourseServiceImpl courseServiceImpl;

    private List<Course> sampleCourses;
    private Course sampleCourse;
    private List<CourseDTO> sampleCoursesDTO;
    private CourseDTO sampleCourseDTO;
    private String id;

    @BeforeEach
    public void initializeVariables(){
        sampleCourses = Arrays.asList(
                Course.builder().id("course-id-1").name( "Computer Networks").creditHr(4).build(),
                Course.builder().id("course-id-2").name("Advanced Java").creditHr(5).build());

        sampleCourse = Course.builder().id("course-id-3").name("Advanced Java").creditHr(5).build();

        sampleCoursesDTO = Arrays.asList(
                CourseDTO.builder().id("course-id-1").name( "Computer Networks").creditHr(4).build(),
                CourseDTO.builder().id("course-id-2").name("Advanced Java").creditHr(5).build());

        sampleCourseDTO = CourseDTO.builder().id("course-id-3").name("Advanced Java").creditHr(5).build();

//        sampleCourseDTO = iMapper.courseEntityToDto(sampleCourse);
//        sampleCoursesDTO = sampleCourses.stream().map(iMapper::courseEntityToDto).collect(Collectors.toList());
//        Mockito.when(iMapper.courseEntityToDto(sampleCourse)).thenReturn(sampleCourseDTO);
//        Mockito.when(iMapper.courseDtoToEntity(sampleCourseDTO)).thenReturn(sampleCourse);
        id = sampleCourse.getId();

    }

    @Test
    void testAddCourse() {
        Mockito.when(courseRepositoryMock.save(sampleCourse)).thenReturn(sampleCourse);
        Mockito.when(iMapper.courseEntityToDto(sampleCourse)).thenReturn(sampleCourseDTO);
        Mockito.when(iMapper.courseDtoToEntity(sampleCourseDTO)).thenReturn(sampleCourse);
        CourseDTO responseDTO = courseServiceImpl.addCourse(sampleCourseDTO);
        Mockito.verify(courseRepositoryMock).save(sampleCourse);
        assertEquals(sampleCourseDTO, responseDTO);
    }

    @Test
    void testGetCourses() {

        Mockito.when(courseRepositoryMock.findAll()).thenReturn(sampleCourses);
        Mockito.when(iMapper.courseEntityToDto(sampleCourses.get(0))).thenReturn(sampleCoursesDTO.get(0));
        Mockito.when(iMapper.courseEntityToDto(sampleCourses.get(1))).thenReturn(sampleCoursesDTO.get(1));
        List<CourseDTO> result = courseServiceImpl.getCourses();
        Mockito.verify(courseRepositoryMock).findAll();
        assertEquals(sampleCoursesDTO, result);
    }


    @Test
    void testGetCourseById() {
        Mockito.when(courseRepositoryMock.findById(id)).thenReturn(Optional.of(sampleCourse));
        Mockito.when(iMapper.courseEntityToDto(sampleCourse)).thenReturn(sampleCourseDTO);
        CourseDTO result = courseServiceImpl.getCourseById(id);
        Mockito.verify(courseRepositoryMock).findById(id);
        assertEquals(sampleCourseDTO, result);
    }

    @Test
    void testDeleteCourseById(){
        courseServiceImpl.deleteCourseById(id);
        Mockito.verify(courseRepositoryMock).deleteById(id);
    }

    @Test
    void testCheckIfCourseExists(){
        Mockito.when(courseRepositoryMock.existsById(id)).thenReturn(true);
        boolean result = courseServiceImpl.checkIfCourseExists(id);
        Mockito.verify(courseRepositoryMock).existsById(id);
        assertTrue(result);
    }

}