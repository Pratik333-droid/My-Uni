package myuni.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myuni.DTOs.CourseDTO;
import myuni.Services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getCourses(){
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> addCourse(@RequestBody @Valid CourseDTO courseDTO){

        return new ResponseEntity<>(courseService.addCourse(courseDTO), HttpStatus.OK);
    }

    // get a course with a particular id
    @GetMapping(path = "/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable String id){
            return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    // delete a course with a particular id
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable String id){

        if (!courseService.checkIfCourseExists(id))
            return new ResponseEntity<>("Course with id "+id+" doesn't exist.", HttpStatus.NOT_FOUND);

        try{
            courseService.deleteCourseById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Course with id "+id+" deleted.");
        } catch (Exception exp2){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exp2.getMessage());
            return new ResponseEntity<>(exp2.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update a course with id

    @PutMapping(path = "/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable String id, @Valid @RequestBody CourseDTO newCourseDTO){
        if (! courseService.checkIfCourseExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        newCourseDTO.setId(id);
        try{
            CourseDTO courseDTO = courseService.addCourse(newCourseDTO);
            return new ResponseEntity<>(courseDTO, HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
