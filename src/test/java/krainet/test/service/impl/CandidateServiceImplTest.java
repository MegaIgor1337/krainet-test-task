package krainet.test.service.impl;

import krainet.test.util.CandidateTestData;
import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.repository.CandidateRepository;
import krainet.test.service.dto.CandidateRequestDto;
import krainet.test.service.dto.CandidateResponseDto;
import krainet.test.service.mapper.CandidateMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceImplTest {
    @InjectMocks
    private CandidateServiceImpl candidateService;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private CandidateMapper candidateMapper;

    @Test
    public void shouldReturnTrueWhenCandidateExist() {
        when(candidateRepository.existsById(any(Long.class))).thenReturn(true);

        boolean result = candidateService.isCandidateExist(1L);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenCandidateNotExist() {
        when(candidateRepository.existsById(any(Long.class))).thenReturn(false);

        boolean result = candidateService.isCandidateExist(1L);

        assertFalse(result);
    }

    @Test
    public void shouldUpdateCandidateCorrectWithDirections() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithDirectionsId();
        Candidate candidate = CandidateTestData.createCandidateWithDirections();
        CandidateResponseDto candidateResponseDto = CandidateTestData.createResponseDtoWithDirections();

        when(candidateMapper.fromRequestDtoToEntity(candidateRequestDto)).thenReturn(candidate);
        candidate.setId(1L);
        when(candidateRepository.save(candidate)).thenReturn(candidate);
        when(candidateMapper.fromEntityToResponseDto(candidate)).thenReturn(candidateResponseDto);

        CandidateResponseDto response = candidateService.updateCandidate(1L, candidateRequestDto);

        assertEquals(candidateRequestDto.firstName(), response.firstName());
        assertEquals(candidateRequestDto.lastName(), response.lastName());
        assertEquals(candidateRequestDto.fatherName(), response.fatherName());
        assertEquals(candidateRequestDto.description(), response.description());
        assertEquals(candidateRequestDto.directionsId().size(), response.directionsId().size());

    }


    @Test
    public void shouldUpdateCandidateCorrectWithoutDirections() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithoutDirectionsId();
        Candidate candidate = CandidateTestData.createCandidateWithoutDirectionId();
        CandidateResponseDto candidateResponseDto = CandidateTestData.createResponseDtoWithoutDirections();

        when(candidateMapper.fromRequestDtoToEntity(candidateRequestDto)).thenReturn(candidate);
        candidate.setId(1L);
        when(candidateRepository.save(candidate)).thenReturn(candidate);
        when(candidateMapper.fromEntityToResponseDto(candidate)).thenReturn(candidateResponseDto);

        CandidateResponseDto response = candidateService.updateCandidate(1L, candidateRequestDto);

        assertEquals(candidateRequestDto.firstName(), response.firstName());
        assertEquals(candidateRequestDto.lastName(), response.lastName());
        assertEquals(candidateRequestDto.fatherName(), response.fatherName());
        assertEquals(candidateRequestDto.description(), response.description());
    }

    @Test
    public void shouldSavedCandidateCorrectWithoutDirection() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithoutDirectionsId();
        Candidate candidate = CandidateTestData.createCandidateWithoutDirectionId();
        CandidateResponseDto candidateResponseDto = CandidateTestData.createResponseDtoWithoutDirections();

        when(candidateMapper.fromRequestDtoToEntity(candidateRequestDto)).thenReturn(candidate);
        when(candidateRepository.save(candidate)).thenReturn(candidate);
        when(candidateMapper.fromEntityToResponseDto(candidate)).thenReturn(candidateResponseDto);

        CandidateResponseDto response = candidateService.saveCandidate(candidateRequestDto);

        assertEquals(candidateRequestDto.firstName(), response.firstName());
        assertEquals(candidateRequestDto.lastName(), response.lastName());
        assertEquals(candidateRequestDto.fatherName(), response.fatherName());
        assertEquals(candidateRequestDto.description(), response.description());
    }

    @Test
    public void shouldSaveCandidateCorrectWithDirections() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithDirectionsId();
        Candidate candidate = CandidateTestData.createCandidateWithDirections();
        CandidateResponseDto candidateResponseDto = CandidateTestData.createResponseDtoWithDirections();

        when(candidateMapper.fromRequestDtoToEntity(candidateRequestDto)).thenReturn(candidate);
        when(candidateRepository.save(candidate)).thenReturn(candidate);
        when(candidateMapper.fromEntityToResponseDto(candidate)).thenReturn(candidateResponseDto);

        CandidateResponseDto response = candidateService.saveCandidate(candidateRequestDto);

        assertEquals(candidateRequestDto.firstName(), response.firstName());
        assertEquals(candidateRequestDto.lastName(), response.lastName());
        assertEquals(candidateRequestDto.fatherName(), response.fatherName());
        assertEquals(candidateRequestDto.description(), response.description());
        assertEquals(candidateRequestDto.directionsId().size(), response.directionsId().size());
    }
}
