package myuni.DAOs;

import myuni.Entities.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, String> {
}
