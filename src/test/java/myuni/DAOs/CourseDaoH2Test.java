package myuni.DAOs;

import myuni.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDaoH2Test extends JpaRepository<Course, String> {
    Iterable<Course> creditHrLessThan(int creditHr);
}
