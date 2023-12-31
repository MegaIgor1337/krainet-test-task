package krainet.test.service.mapper;

import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.entity.Direction;
import krainet.test.persistence.repository.DirectionRepository;
import krainet.test.service.dto.CandidateRequestDto;
import krainet.test.service.dto.CandidateResponseDto;
import krainet.test.util.CandidateTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static krainet.test.util.DirectionTestData.createListOfDirections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CandidateMapperTest {
    @Mock
    private DirectionRepository directionRepository;
    @InjectMocks
    private CandidateMapper candidateMapper;

    @Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDtoWithoutDirections() throws IOException {
        Candidate entity = CandidateTestData.createCandidateWithoutDirectionId();
        CandidateResponseDto responseDto = candidateMapper.fromEntityToResponseDto(entity);

        assertEquals(entity.getDescription(), responseDto.description());
        assertEquals(entity.getFirstName(), responseDto.firstName());
        assertEquals(entity.getLastName(), responseDto.lastName());
        assertEquals(entity.getFatherName(), responseDto.fatherName());
        assertEquals(entity.getDescription(), responseDto.description());

    }

    @Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDtoWithDirections() throws IOException {
        Candidate entity = CandidateTestData.createCandidateWithDirections();

        CandidateResponseDto responseDto = candidateMapper.fromEntityToResponseDto(entity);

        assertEquals(entity.getDescription(), responseDto.description());
        assertEquals(entity.getFirstName(), responseDto.firstName());
        assertEquals(entity.getLastName(), responseDto.lastName());
        assertEquals(entity.getFatherName(), responseDto.fatherName());
        assertEquals(entity.getDescription(), responseDto.description());

    }

    @Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntityWithDirections() throws IOException {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithDirectionsId();
        List<Direction> directions = createListOfDirections();

        when(directionRepository.findById(any(Long.class))).thenReturn(
                Optional.of(directions.get(0)), Optional.of(directions.get(1)));

        Candidate candidate = candidateMapper.fromRequestDtoToEntity(candidateRequestDto);

        assertEquals(candidateRequestDto.description(), candidate.getDescription());
        assertEquals(candidateRequestDto.firstName(), candidate.getFirstName());
        assertEquals(candidateRequestDto.lastName(), candidate.getLastName());
        assertEquals(candidateRequestDto.fatherName(), candidate.getFatherName());
        assertEquals(candidateRequestDto.directionsId().size(), candidate.getDirections().size());

    }

    @Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntityWithoutDirections() throws IOException {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithoutDirectionsId();

        Candidate candidate = candidateMapper.fromRequestDtoToEntity(candidateRequestDto);

        assertEquals(candidateRequestDto.description(), candidate.getDescription());
        assertEquals(candidateRequestDto.firstName(), candidate.getFirstName());
        assertEquals(candidateRequestDto.lastName(), candidate.getLastName());
        assertEquals(candidateRequestDto.fatherName(), candidate.getFatherName());
    }


}
