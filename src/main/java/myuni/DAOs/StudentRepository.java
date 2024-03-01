package myuni.DAOs;

import myuni.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    public Optional<Student> findByEmail(String email);
}
