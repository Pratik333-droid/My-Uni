package myuni.Services.Impl;

import lombok.RequiredArgsConstructor;
import myuni.DTOs.UniversityDTO;
import myuni.Entities.University;
import myuni.DAOs.UniversityRepository;
import myuni.Exceptions.UniversityNotFoundException;
import myuni.Mappers.IMapper;
import myuni.Services.UniversityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// the @Service annotation tells the spring boot that the following class
// is a service class
@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final IMapper iMapper;
    @Override
    public List<UniversityDTO> getUnis() {
        return universityRepository.findAll().stream().map(iMapper::universityEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public UniversityDTO addUni(UniversityDTO universityDTO) {
        return iMapper.universityEntityToDTO(universityRepository.save(iMapper.universityDtoToEntity(universityDTO)));
    }

    @Override
    public UniversityDTO getUniById(String id) {
        return iMapper.universityEntityToDTO(universityRepository.findById(id).orElseThrow(()-> new UniversityNotFoundException("University with id "+ id + "doesn't exist")));
    }

    @Override
    public boolean checkIfUniExists(String id){
        return universityRepository.existsById(id);
    }

    @Override
    public void deleteUniById(String id) {
        universityRepository.deleteById(id);
    }
}
