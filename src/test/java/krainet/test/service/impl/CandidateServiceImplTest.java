package krainet.test.service.impl;

import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.repository.CandidateRepository;
import krainet.test.service.dto.*;
import krainet.test.service.mapper.CandidateMapper;
import krainet.test.util.CandidateTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static krainet.test.util.CandidateTestData.*;
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

    @Test
    public void shouldGetCorrectListOfCandidatesWithoutAnyParams() {
        List<Candidate> candidates = createListOfCandidates();
        Page<Candidate> page = new PageImpl<>(candidates);
        List<CandidateResponseDto> candidateResponseDtos = createListOfCandidateResponseDto();

        setUpMocks(page, candidateResponseDtos);

        List<CandidateResponseDto> result = candidateService.getCandidates(null, null,
                null, null);

        assertNotNull(result);
        assertEquals(page.getContent().size(), result.size());
        assertEquals(candidates.get(0).getId(), result.get(0).id());
        assertEquals(candidates.get(0).getFirstName(), result.get(0).firstName());
        assertEquals(candidates.get(1).getLastName(), result.get(1).lastName());
    }

    @Test
    public void shouldReturnListOfCandidatesWhenPageRequestDtoExist() {
        PageRequestDto pageRequestDto = createPageRequestDto();
        List<Candidate> candidates = createListOfCandidates();
        Page<Candidate> page = new PageImpl<>(candidates);
        List<CandidateResponseDto> candidateResponseDtos = createListOfCandidateResponseDto();

        setUpMocks(page, candidateResponseDtos);

        List<CandidateResponseDto> result = candidateService.getCandidates(null, null,
                null, pageRequestDto);

        assertNotNull(result);
        assertEquals(page.getContent().size(), result.size());
        assertEquals(candidates.get(0).getId(), result.get(0).id());
        assertEquals(candidates.get(0).getFirstName(), result.get(0).firstName());
        assertEquals(candidates.get(1).getLastName(), result.get(1).lastName());
    }


    @Test
    public void shouldReturnListOfCandidatesWhenPageRequestDtoAndSortExist() {
        List<SortCandidateFields> sortCandidateFields = createListOfSortCandidateFields();
        PageRequestDto pageRequestDto = createPageRequestDto();
        List<Candidate> candidates = createListOfCandidates();
        Page<Candidate> page = new PageImpl<>(candidates);
        List<CandidateResponseDto> candidateResponseDtos = createListOfCandidateResponseDto();

        setUpMocks(page, candidateResponseDtos);

        List<CandidateResponseDto> result = candidateService.getCandidates(sortCandidateFields, Sort.Direction.ASC,
                null, pageRequestDto);

        assertNotNull(result);
        assertEquals(page.getContent().size(), result.size());
        assertEquals(candidates.get(0).getId(), result.get(0).id());
        assertEquals(candidates.get(0).getFirstName(), result.get(0).firstName());
        assertEquals(candidates.get(1).getLastName(), result.get(1).lastName());
    }


    @Test
    public void shouldReturnListOfCandidatesWhenAllParamsExist() {
        CandidateRequestFilter candidateRequestFilter = createCandidateFilter();
        List<SortCandidateFields> sortCandidateFields = createListOfSortCandidateFields();
        PageRequestDto pageRequestDto = createPageRequestDto();
        List<Candidate> candidates = createListOfCandidatesAfterFilter();
        Page<Candidate> page = new PageImpl<>(candidates);
        List<CandidateResponseDto> candidateResponseDtos = createListOfCandidateResponseDtoAfterFilter();

        when(candidateRepository.getCountOfDirections()).thenReturn(2);
        when(candidateRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(candidateMapper.fromEntityToResponseDto(any(Candidate.class))).thenReturn(candidateResponseDtos.get(0));

        List<CandidateResponseDto> result = candidateService.getCandidates(sortCandidateFields, Sort.Direction.ASC,
                candidateRequestFilter, pageRequestDto);

        assertNotNull(result);
        assertEquals(candidateRequestFilter.firstName(), result.get(0).firstName());
    }

    private void setUpMocks(Page<Candidate> page, List<CandidateResponseDto> candidateResponseDtos) {
        when(candidateRepository.getCountOfDirections()).thenReturn(2);
        when(candidateRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(candidateMapper.fromEntityToResponseDto(any(Candidate.class))).thenReturn(candidateResponseDtos.get(0),
                candidateResponseDtos.get(1));
    }
}
