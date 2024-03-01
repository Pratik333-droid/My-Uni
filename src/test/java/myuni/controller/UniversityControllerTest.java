package myuni.controller;

import lombok.RequiredArgsConstructor;
import myuni.DTOs.UniversityDTO;
import myuni.Entities.University;
import myuni.DAOs.UniversityDaoH2Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UniversityControllerTest {
    @LocalServerPort
    private int port;
    private String url = "http://localhost:";
    private static RestTemplate restTemplate;

    @Autowired
    private UniversityDaoH2Test universityDaoH2Test;
    private final ParameterizedTypeReference<List<UniversityDTO>> responseType = new ParameterizedTypeReference<>(){};
    @BeforeAll
    public static void initRestTemplate(){
        restTemplate = new RestTemplate();
    }
    @BeforeEach
    public void setUrl(){
        url = url.concat(port+"/uni");
    }

    @Test
    @Sql(statements = "DELETE from university", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE from university", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testAddUni(){
        UniversityDTO universityDTO = UniversityDTO.builder().name("MIT").location("Massachuets, USA").globalRank(3).build();

        HttpEntity<UniversityDTO> request = new HttpEntity<>(universityDTO);

        ResponseEntity<UniversityDTO> response = restTemplate.exchange(url, HttpMethod.POST, request, UniversityDTO.class);

        assertNotNull(response.getBody());

        University repoResponse = universityDaoH2Test.findById(response.getBody().getId()).orElse(null);

        assertNotNull(repoResponse);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getName(), repoResponse.getName());
        assertEquals(1, universityDaoH2Test.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO University (id, university_name, global_rank, location) values ('5' ,'MIT', 3, 'Massachhuets, USA'), ('6','Princeton', 9, 'USA')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE from university", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetUnis(){
        ResponseEntity<List<UniversityDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(2, universityDaoH2Test.findAll().size());
        assertEquals(response.getBody().get(0).getName(), "MIT");
        assertEquals(response.getBody().get(1).getLocation(), "USA");
    }

    @Test
    @Sql(statements = "INSERT INTO University (id, university_name, global_rank, location) values ('619', 'UC Berkley', 110, 'California')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE from University where id = '619'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetUniById(){
        url = url.concat("/619");

        ResponseEntity<UniversityDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, UniversityDTO.class);

        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals("619", response.getBody().getId());

    }

    @Test
    @Sql(statements = "INSERT INTO University (id, university_name, global_rank, location) values ('619', 'MIT', 1, 'Massachuets')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteUniById(){
        url = url.concat("/619");
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(universityDaoH2Test.existsById("619"));
    }

    @Test
    @Sql(statements = "INSERT INTO University (id, university_name, global_rank, location) values ('619', 'MIT', 100, 'Machhapuchhre')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM University where id = '619'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateUniById(){
        url = url.concat("/619");
        UniversityDTO universityDTO = new UniversityDTO("619", "MIT", "Massachuets", 1);

        HttpEntity<UniversityDTO> request = new HttpEntity<>(universityDTO);
        ResponseEntity<UniversityDTO> response = restTemplate.exchange(url, HttpMethod.PUT, request, UniversityDTO.class);

        University repoResponse = universityDaoH2Test.findById(universityDTO.getId()).orElse(null);

        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(repoResponse);
        assertEquals(repoResponse.getId(), response.getBody().getId());

    }
}
