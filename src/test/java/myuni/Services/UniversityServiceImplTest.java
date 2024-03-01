package myuni.Services;

import myuni.DTOs.UniversityDTO;
import myuni.Entities.University;
import myuni.DAOs.UniversityRepository;
import myuni.Mappers.IMapper;
import myuni.Services.Impl.UniversityServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class UniversityServiceImplTest {

    @Mock
    private UniversityRepository universityRepositoryMock;

    @Mock
    private IMapper iMapper;
    @InjectMocks
    private UniversityServiceImpl universityServiceImpl;

    private List<University> sampleUnis;
    private University sampleUni;
    private List<UniversityDTO> sampleUnisDTO;
    private UniversityDTO sampleUniDTO;
    private String id;

    @BeforeEach
    public void initializeVariables(){

        sampleUnis = Arrays.asList(University.builder().id("619").name("MIT").location("USA").globalRank(1).build(), University.builder().id("620").name("Harvard").location("USA").globalRank(2).build());

        sampleUni = University.builder().id("619").name("Harvard University").location("USA").globalRank(2).build();

        sampleUnisDTO = Arrays.asList(UniversityDTO.builder().id("619").name("MIT").location("USA").globalRank(1).build(), UniversityDTO.builder().id("620").name("Harvard").location("USA").globalRank(2).build());

        sampleUniDTO = UniversityDTO.builder().id("619").name("Harvard University").location("USA").globalRank(2).build();

        id = sampleUni.getId();

    }

    @Test
    void testAddUni(){
        Mockito.when(universityRepositoryMock.save(sampleUni)).thenReturn(sampleUni);
        Mockito.when(iMapper.universityEntityToDTO(sampleUni)).thenReturn(sampleUniDTO);
        Mockito.when(iMapper.universityDtoToEntity(sampleUniDTO)).thenReturn(sampleUni);
        UniversityDTO response = universityServiceImpl.addUni(sampleUniDTO);
        Mockito.verify(universityRepositoryMock).save(sampleUni);
        assertEquals(sampleUniDTO, response);
    }

    @Test
    void testGetUnis(){

        Mockito.when(universityRepositoryMock.findAll()).thenReturn(sampleUnis);
        Mockito.when(iMapper.universityEntityToDTO(sampleUnis.get(0))).thenReturn(sampleUnisDTO.get(0));
        Mockito.when(iMapper.universityEntityToDTO(sampleUnis.get(1))).thenReturn(sampleUnisDTO.get(1));
        List<UniversityDTO> response = universityServiceImpl.getUnis();
        Mockito.verify(universityRepositoryMock).findAll();
        assertEquals(sampleUnisDTO, response);

    }

    @Test
    void testGetUniById(){

        Mockito.when(universityRepositoryMock.findById(id)).thenReturn(Optional.of(sampleUni));
        Mockito.when(iMapper.universityEntityToDTO(sampleUni)).thenReturn(sampleUniDTO);
        UniversityDTO response = universityServiceImpl.getUniById(id);

        Mockito.verify(universityRepositoryMock).findById(id);
        assertEquals(sampleUniDTO, response);
    }

    @Test
    void testDeleteUniById(){
        universityServiceImpl.deleteUniById(id);
        Mockito.verify(universityRepositoryMock).deleteById(id);
    }

    @Test
    void testCheckIfUniExists(){

        Mockito.when(universityServiceImpl.checkIfUniExists(id)).thenReturn(false);
        boolean result = universityServiceImpl.checkIfUniExists(id);
        Mockito.verify(universityRepositoryMock).existsById(id);
        assertFalse(result);
    }
}
