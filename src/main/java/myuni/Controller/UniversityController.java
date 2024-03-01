package myuni.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myuni.DTOs.UniversityDTO;
import myuni.Entities.University;
import myuni.Services.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/uni")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("/homepage")
    public String homePage(){
        return "University Home page";
    }

    // returns all the universities
    @GetMapping
    public List<UniversityDTO> getUnis(){
        return universityService.getUnis();
    }

    // adds a particular university
    @PostMapping
    public UniversityDTO addUni(@RequestBody @Valid UniversityDTO university){
        return this.universityService.addUni(university);
    }

    // returns about a university with a particular id
    @GetMapping("/{id}")
    public UniversityDTO getUni(@PathVariable String id){
        return universityService.getUniById(id);
    }

    // deletes a university with a particular id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWithId(@PathVariable String id){
        if (!universityService.checkIfUniExists(id))
            return new ResponseEntity<>("University with id "+ id + "doesn't exist" , HttpStatus.NOT_FOUND);

        try{
            universityService.deleteUniById(id);
            return new ResponseEntity<>("University with id "+id+" deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update a university with a particular id
    @PutMapping(path = "/{id}")
    public ResponseEntity<UniversityDTO> updateUniById(@PathVariable String id, @Valid @RequestBody UniversityDTO uni){
        if (!universityService.checkIfUniExists(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        uni.setId(id);
        try{
            UniversityDTO newUni = universityService.addUni(uni);
            return new ResponseEntity<>(newUni, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
