package org.example.service.impl;

import org.example.persistence.entity.Candidate;
import org.example.persistence.repository.CandidateRepository;
import org.example.service.dto.CandidateRequestDto;
import org.example.service.dto.CandidateResponseDto;
import org.example.service.mapper.CandidateMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.util.CandidateTestData.*;
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
        CandidateRequestDto candidateRequestDto = createRequestDtoWithDirectionsId();
        Candidate candidate = createCandidateWithDirections();
        CandidateResponseDto candidateResponseDto = createResponseDtoWithDirections();

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
        CandidateRequestDto candidateRequestDto = createRequestDtoWithoutDirectionsId();
        Candidate candidate = createCandidateWithoutDirectionId();
        CandidateResponseDto candidateResponseDto = createResponseDtoWithoutDirections();

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
        CandidateRequestDto candidateRequestDto = createRequestDtoWithoutDirectionsId();
        Candidate candidate = createCandidateWithoutDirectionId();
        CandidateResponseDto candidateResponseDto = createResponseDtoWithoutDirections();

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
        CandidateRequestDto candidateRequestDto = createRequestDtoWithDirectionsId();
        Candidate candidate = createCandidateWithDirections();
        CandidateResponseDto candidateResponseDto = createResponseDtoWithDirections();

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
