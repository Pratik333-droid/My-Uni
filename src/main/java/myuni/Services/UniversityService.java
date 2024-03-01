package myuni.Services;

import myuni.DTOs.UniversityDTO;
import myuni.Entities.University;
import java.util.List;
import java.util.Optional;

public interface UniversityService {

    public List<UniversityDTO> getUnis();
    public UniversityDTO addUni(UniversityDTO universityDTO);
    public UniversityDTO getUniById(String id);
    public boolean checkIfUniExists(String id);
    public void deleteUniById(String id);
}
