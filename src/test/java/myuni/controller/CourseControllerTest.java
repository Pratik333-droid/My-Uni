package myuni.controller;

import lombok.RequiredArgsConstructor;
import myuni.DAOs.CourseDaoH2Test;
import myuni.DTOs.CourseDTO;
import myuni.Entities.Course;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseControllerTest {
    @LocalServerPort
    private int port;

    private String url = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private CourseDaoH2Test courseDaoH2Test;

    private final ParameterizedTypeReference<List<CourseDTO>> responseType =
            new ParameterizedTypeReference<List<CourseDTO>>() {};

    @BeforeAll
    public static void initRestTemplate(){
        restTemplate = new RestTemplate();
    }
    @BeforeEach
    public void setUp(){
        url = url.concat(":").concat(port+"/course");
        System.out.println("base url = "+url);
    }

//    @Disabled
    @Test
    @Sql(statements = "DELETE from course", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE from course", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testAddCourse(){
        CourseDTO courseDTO = CourseDTO.builder().id("id").name("DSA").creditHr(4).build();
        HttpEntity<CourseDTO> request = new HttpEntity<>(courseDTO);
        ResponseEntity<CourseDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, CourseDTO.class);

        assertNotNull(response.getBody(), "endpoint "+url+" on post request returns null");
        assertEquals(courseDTO.getName(), response.getBody().getName());
        assertEquals(1, courseDaoH2Test.findAll().size());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

//    @Disabled
    @Test
    @Sql(statements = "INSERT INTO Course(id, course_name, credit_hours) VALUES ('619' ,'DBMS', 3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO Course(id, course_name, credit_hours) VALUES ('620' ,'DBMS', 3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM Course", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetCourses(){
        ResponseEntity <List<CourseDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
//        System.out.println("Courses = "+courses);
//        assertEquals(1, courses.size());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().get(0).getId(), "619");
        assertEquals(response.getBody().get(1).getId(), "620");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(2, courseDaoH2Test.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO Course(id, course_name, credit_hours) VALUES ('619' ,'DBMS', 3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE from Course where id = '619'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetCourseById(){
        url = url.concat("/619");
        ResponseEntity<CourseDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, CourseDTO.class);

        assertNotNull(response.getBody());
        assertEquals(response.getBody().getId(), "619");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Sql(statements = "INSERT INTO Course(id, course_name, credit_hours) VALUES ('619', 'DBMS', 3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteCourseById(){
        url = url.concat("/619");
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertFalse(courseDaoH2Test.existsById("619"));
        assertEquals(0, courseDaoH2Test.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO Course(id, course_name, credit_hours) values ('619', 'DDMS', 31)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE from Course where id = '619'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateCourseById(){
        url = url.concat("/619");
        CourseDTO courseDTO = new CourseDTO("619", "DBMS", 3);
        HttpEntity<CourseDTO> request = new HttpEntity<>(courseDTO);
        ResponseEntity<CourseDTO> response = restTemplate.exchange(url, HttpMethod.PUT, request, CourseDTO.class);

        assertNotNull(response.getBody());
        Course repoResponse = courseDaoH2Test.findById("619").orElse(null);
        assertNotNull(repoResponse);
        assertEquals(response.getBody().getName(), repoResponse.getName());
        assertEquals(response.getBody().getCreditHr(), repoResponse.getCreditHr());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        if(courseDaoH2Test.existsById("619")){
//            assertEquals("DBMS", response.getName());
//            assertEquals(3, response.getCreditHr());
//        }
//        else
//            fail("Test case failed.");
    }

    @Test
    @Sql(statements = "INSERT INTO Course(id, course_name, credit_hours) values ('619', 'DDMS', 3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE From Course where id = '619'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testCustomQueries(){
        System.out.println(courseDaoH2Test.creditHrLessThan(6));
        Iterable<Course> coursesWithCreditHrLessThanSix = courseDaoH2Test.creditHrLessThan(6);
        assertNotNull(coursesWithCreditHrLessThanSix);
        for (Course course: coursesWithCreditHrLessThanSix){
            assert(course.getCreditHr()<6);
        }
    }
}
