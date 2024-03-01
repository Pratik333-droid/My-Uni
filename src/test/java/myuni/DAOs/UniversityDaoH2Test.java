package myuni.DAOs;

import myuni.Entities.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityDaoH2Test extends JpaRepository<University, String> {
}
