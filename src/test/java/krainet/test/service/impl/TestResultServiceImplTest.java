package krainet.test.service.impl;

import krainet.test.persistence.entity.TestResult;
import krainet.test.persistence.repository.TestResultRepository;
import krainet.test.service.dto.*;
import krainet.test.service.mapper.TestResultMapper;
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

import static krainet.test.util.CandidateTestData.createPageRequestDto;
import static krainet.test.util.TestResultTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestResultServiceImplTest {
    @InjectMocks
    private TestResultServiceImpl testResultService;
    @Mock
    private TestResultRepository testResultRepository;
    @Mock
    private TestResultMapper testResultMapper;

    @Test
    public void shouldCorrectlySaveTestResultRequestDto() {
        TestResultRequestDto testResultRequestDto = createTestResultRequestDto();
        TestResult testResult = createTestResultEntity();
        TestResult testResultWithId = createTestResultEntityWithId();
        TestResultResponseDto testResultResponseDto = createTestResultResponseDto();

        when(testResultMapper.fromRequestDtoToEntity(testResultRequestDto)).thenReturn(testResult);
        when(testResultRepository.save(testResult)).thenReturn(testResultWithId);
        when(testResultMapper.fromEntityToResponseDto(testResultWithId)).thenReturn(testResultResponseDto);

        TestResultResponseDto result = testResultService.saveTestResult(testResultRequestDto);

        assertNotNull(result);
        assertEquals(testResultRequestDto.testId(), result.testId());
        assertEquals(testResultRequestDto.candidateId(), result.candidateId());
        assertEquals(testResultRequestDto.score(), result.score());
    }

    @Test
    public void shouldCorrectlyUpdateTestResult() {
        TestResultRequestDto testResultRequestDto = createTestResultRequestDto();
        TestResult testResult = createTestResultEntity();
        TestResultResponseDto testResultResponseDto = createTestResultResponseDto();

        when(testResultMapper.fromRequestDtoToEntity(testResultRequestDto)).thenReturn(testResult);
        testResult.setId(1L);
        when(testResultRepository.save(testResult)).thenReturn(testResult);
        when(testResultMapper.fromEntityToResponseDto(testResult)).thenReturn(testResultResponseDto);

        TestResultResponseDto result = testResultService.updateTestResult(1L, testResultRequestDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(testResultRequestDto.testId(), result.testId());
        assertEquals(testResultRequestDto.candidateId(), result.candidateId());
        assertEquals(testResultRequestDto.score(), result.score());
    }

    @Test
    public void shouldGetCorrectListOfTestResultWithoutAnyParams() {
        List<TestResult> testResults = createLisOfTestResultsWithId();
        Page<TestResult> page = new PageImpl<>(testResults);
        List<TestResultResponseDto> testResultResponseDtos = createListOfTestResultResponseDto();

        setUpMocks(page, testResultResponseDtos);

        List<TestResultResponseDto> result = testResultService.getTestResult(null, null,
                null, null);

        assertNotNull(result);
        assertEquals(page.getContent().size(), result.size());
        assertEquals(testResults.get(0).getId(), result.get(0).id());
        assertEquals(testResults.get(0).getScore(), result.get(0).score());
        assertEquals(testResults.get(1).getTest().getId(), result.get(1).testId());
    }

    @Test
    public void shouldReturnListOfTestResultWhenPageRequestDtoExist() {
        PageRequestDto pageRequestDto = createPageRequestDto();
        List<TestResult> testResults = createLisOfTestResultsWithId();
        Page<TestResult> page = new PageImpl<>(testResults);
        List<TestResultResponseDto> testResultResponseDtos = createListOfTestResultResponseDto();

        setUpMocks(page, testResultResponseDtos);

        List<TestResultResponseDto> result = testResultService.getTestResult(null, null,
                null, pageRequestDto);

        assertNotNull(result);
        assertEquals(page.getContent().size(), result.size());
        assertEquals(testResults.get(0).getId(), result.get(0).id());
        assertEquals(testResults.get(0).getTest().getId(), result.get(0).testId());
        assertEquals(testResults.get(1).getScore(), result.get(1).score());
    }


    @Test
    public void shouldReturnListOfTestResultsWhenPageRequestDtoAndSortExist() {
        List<SortTestResultFields> sortTestResultFields = createListOfSortTestResultFields();
        PageRequestDto pageRequestDto = createPageRequestDto();
        List<TestResult> testResults = createLisOfTestResultsWithId();
        Page<TestResult> page = new PageImpl<>(testResults);
        List<TestResultResponseDto> testResultResponseDtos = createListOfTestResultResponseDto();

        setUpMocks(page, testResultResponseDtos);

        List<TestResultResponseDto> result = testResultService.getTestResult(sortTestResultFields, Sort.Direction.ASC,
                null, pageRequestDto);

        assertNotNull(result);
        assertEquals(page.getContent().size(), result.size());
        assertEquals(testResults.get(0).getId(), result.get(0).id());
        assertEquals(testResults.get(0).getCandidate().getId(), result.get(0).candidateId());
        assertEquals(testResults.get(1).getScore(), result.get(1).score());
    }


    @Test
    public void shouldReturnListOfCandidatesWhenAllParamsExist() {
        TestResultRequestFilter testResultRequestFilter = createTestResultFilter();
        List<SortTestResultFields> sortTestResultFields = createListOfSortTestResultFields();
        PageRequestDto pageRequestDto = createPageRequestDto();
        List<TestResult> testResults = createLisOfTestResultsWithId();
        Page<TestResult> page = new PageImpl<>(testResults);
        List<TestResultResponseDto> testResultResponseDtos = createListOfTestResultResponseDto();

        when(testResultRepository.getCountOfTestResults()).thenReturn(2);
        when(testResultRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(testResultMapper.fromEntityToResponseDto(any(TestResult.class))).thenReturn(testResultResponseDtos.get(0));

        List<TestResultResponseDto> result = testResultService.getTestResult(sortTestResultFields, Sort.Direction.ASC,
                testResultRequestFilter, pageRequestDto);

        assertNotNull(result);
        assertEquals(testResultRequestFilter.score(), result.get(0).score());
    }

    @Test
    public void shouldReturnTrueWhenIsTestResultExist() {
        when(testResultRepository.existsById(TEST_RESULT_ID)).thenReturn(true);

        boolean result = testResultService.isTestResultExist(TEST_RESULT_ID);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenIsTestResultExist() {
        when(testResultRepository.existsById(TEST_RESULT_ID)).thenReturn(false);

        boolean result = testResultService.isTestResultExist(TEST_RESULT_ID);

        assertFalse(result);
    }

    private void setUpMocks(Page<TestResult> page, List<TestResultResponseDto> testResultResponseDtos) {
        when(testResultRepository.getCountOfTestResults()).thenReturn(2);
        when(testResultRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(testResultMapper.fromEntityToResponseDto(any(TestResult.class))).thenReturn(testResultResponseDtos.get(0),
                testResultResponseDtos.get(1));
    }

}
