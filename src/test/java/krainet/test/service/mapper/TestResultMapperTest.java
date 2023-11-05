package krainet.test.service.mapper;

import krainet.test.persistence.entity.TestResult;
import krainet.test.persistence.repository.CandidateRepository;
import krainet.test.persistence.repository.TestRepository;
import krainet.test.service.dto.TestResultRequestDto;
import krainet.test.service.dto.TestResultResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static krainet.test.util.TestResultTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestResultMapperTest {
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private TestRepository testRepository;
    @InjectMocks
    private TestResultMapper testResultMapper;

    @Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        TestResult testResult = createTestResultEntity();

        TestResultResponseDto result = testResultMapper.fromEntityToResponseDto(testResult);

        assertEquals(testResult.getTest().getId(), result.testId());
        assertEquals(testResult.getCandidate().getId(), result.candidateId());
        assertEquals(testResult.getId(), result.id());
        assertEquals(testResult.getScore(), result.score());
    }

    @Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntity() {
        TestResultRequestDto testResultRequestDto = createTestResultRequestDto();

        when(testRepository.findById(1L)).thenReturn(Optional.of(createTestForTestResult()));
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(createCandidateForTestResult()));

        TestResult result = testResultMapper.fromRequestDtoToEntity(testResultRequestDto);

        assertEquals(testResultRequestDto.testId(), result.getTest().getId());
        assertEquals(testResultRequestDto.candidateId(), result.getCandidate().getId());
        assertEquals(testResultRequestDto.score(), result.getScore());
    }
}
