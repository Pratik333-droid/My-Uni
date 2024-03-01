package myuni.Services.Impl;

import lombok.RequiredArgsConstructor;
import myuni.DAOs.StudentRepository;
import myuni.Entities.Student;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomStudentService implements UserDetailsService {
    private final StudentRepository studentRepository;

    // here username means email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Student student = studentRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("stu not found"));
        return studentRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("stu not found"));
    }
}
