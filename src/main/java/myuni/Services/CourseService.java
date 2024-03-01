package myuni.Services;
import myuni.DTOs.CourseDTO;
import java.util.List;

public interface CourseService {
    public List<CourseDTO> getCourses();
    public CourseDTO addCourse(CourseDTO course);
    public CourseDTO getCourseById(String id);
    public void deleteCourseById(String id);
    public boolean checkIfCourseExists(String id);
}
