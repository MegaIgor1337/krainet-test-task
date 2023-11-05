package krainet.test.util;

import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.entity.Direction;
import krainet.test.persistence.entity.Test;
import krainet.test.persistence.entity.TestResult;
import krainet.test.service.dto.SortTestResultFields;
import krainet.test.service.dto.TestResultRequestDto;
import krainet.test.service.dto.TestResultRequestFilter;
import krainet.test.service.dto.TestResultResponseDto;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class TestResultTestData {
    public static final String TEST_RESULT_URL = "/api/v1/tests/results";
    public static Long TEST_RESULT_ID = 1L;
    public static final Integer PAGE_NUMBER = 1;
    public static final Integer PAGE_SIZE = 3;
    public static final Long TEST_ID = 1L;
    public static final Long CANDIDATE_ID = 1L;
    public static final Long INVALID_ID = 123L;

    public static final String CANDIDATE_FIRST_NAME = "Mickael";
    public static String TEST_RESULT_URL_PUT = String.format("%s/%s", TEST_RESULT_URL,
            TEST_RESULT_ID);
    public static String TEST_RESULT_URL_GET_PAGE = String.format("%s?pageNumber=%s&pageSize=%s",
            TEST_RESULT_URL, PAGE_NUMBER, PAGE_SIZE);
    public static String TEST_RESULT_URL_GET_SORTING = String.format("%s?sort=CANDIDATE_FIRST_NAME&order=ASC",
            TEST_RESULT_URL);
    public static String TEST_RESULT_URL_FILTER_AND_PAGE = String
            .format("%s?testId=%s&candidateId=%s&pageNumber=%s&pageSize=%s",
                    TEST_RESULT_URL, TEST_ID, CANDIDATE_ID, PAGE_NUMBER, PAGE_SIZE);
    public static String TEST_RESULT_URL_ALL_PARAMS = String
            .format("%s?sort=CANDIDATE_FIRST_NAME&order=ASC&testId=%s&candidateId=%s&pageNumber=%s&pageSize=%s",
                    TEST_RESULT_URL, TEST_ID, CANDIDATE_ID, PAGE_NUMBER, PAGE_SIZE);
    public static String TEST_RESULT_URL_PUT_INVALID = String
            .format("%s/%s", TEST_RESULT_URL, INVALID_ID);

    public static TestResult createTestResultEntityWithId() {
        return TestResult.builder()
                .id(1L)
                .test(Test.builder()
                        .id(1L)
                        .name("Test 1")
                        .description("Description 1")
                        .directions(List.of(
                                Direction.builder()
                                        .id(1L)
                                        .name("Direction 1")
                                        .description("Description Direction 1")
                                        .build(),
                                Direction.builder()
                                        .id(2L)
                                        .name("Direction 2")
                                        .description("Description 2")
                                        .build()))
                        .build())
                .date(LocalDate.now())
                .score(10)
                .candidate(Candidate.builder()
                        .id(1L)
                        .firstName("First Name 1")
                        .lastName("Last Name 1")
                        .fatherName("Father Name 1")
                        .description("Description 1")
                        .directions(List.of(
                                Direction.builder()
                                        .id(1L)
                                        .name("Direction 1")
                                        .description("Description Direction 1")
                                        .build()
                        ))
                        .build())
                .build();
    }

    public static TestResult createTestResultEntity() {
        return TestResult.builder()
                .test(Test.builder()
                        .id(1L)
                        .name("Test 1")
                        .description("Description 1")
                        .directions(List.of(
                                Direction.builder()
                                        .id(1L)
                                        .name("Direction 1")
                                        .description("Description Direction 1")
                                        .build(),
                                Direction.builder()
                                        .id(2L)
                                        .name("Direction 2")
                                        .description("Description 2")
                                        .build()))
                        .build())
                .date(LocalDate.now())
                .score(10)
                .candidate(Candidate.builder()
                        .id(1L)
                        .firstName("First Name 1")
                        .lastName("Last Name 1")
                        .fatherName("Father Name 1")
                        .description("Description 1")
                        .directions(List.of(
                                Direction.builder()
                                        .id(1L)
                                        .name("Direction 1")
                                        .description("Description Direction 1")
                                        .build()
                        ))
                        .build())
                .build();
    }


    public static TestResultResponseDto createTestResultResponseDto() {
        return TestResultResponseDto.builder()
                .id(1L)
                .date(LocalDate.now())
                .score(10)
                .candidateId(1L)
                .testId(1L)
                .build();
    }

    public static TestResultRequestDto createTestResultRequestDto() {
        return TestResultRequestDto.builder()
                .testId(1L)
                .candidateId(1L)
                .score(10)
                .date(LocalDate.now())
                .build();
    }

    public static Test createTestForTestResult() {
        return Test.builder().id(1L)
                .name("Test 1")
                .description("Description 1")
                .directions(List.of(
                        Direction.builder()
                                .id(1L)
                                .name("Direction 1")
                                .description("Description Direction 1")
                                .build(),
                        Direction.builder()
                                .id(2L)
                                .name("Direction 2")
                                .description("Description 2")
                                .build()))
                .build();
    }

    public static Candidate createCandidateForTestResult() {
        return Candidate.builder()
                .id(1L)
                .firstName("First Name 1")
                .lastName("Last Name 1")
                .fatherName("Father Name 1")
                .description("Description 1")
                .directions(List.of(
                        Direction.builder()
                                .id(1L)
                                .name("Direction 1")
                                .description("Description Direction 1")
                                .build()
                ))
                .build();
    }

    public static List<TestResult> createLisOfTestResults() {
        return List.of(
                TestResult.builder()
                        .test(Test.builder()
                                .id(1L)
                                .name("Test 1")
                                .description("Description 1")
                                .directions(List.of(
                                        Direction.builder()
                                                .id(1L)
                                                .name("Direction 1")
                                                .description("Description Direction 1")
                                                .build(),
                                        Direction.builder()
                                                .id(2L)
                                                .name("Direction 2")
                                                .description("Description 2")
                                                .build()))
                                .build())
                        .date(LocalDate.now())
                        .score(10)
                        .candidate(Candidate.builder()
                                .id(1L)
                                .firstName("First Name 1")
                                .lastName("Last Name 1")
                                .fatherName("Father Name 1")
                                .description("Description 1")
                                .directions(List.of(
                                        Direction.builder()
                                                .id(1L)
                                                .name("Direction 1")
                                                .description("Description Direction 1")
                                                .build()
                                ))
                                .build())
                        .build(),
                TestResult.builder()
                        .test(Test.builder()
                                .id(2L)
                                .name("Test 2")
                                .description("Description 2")
                                .directions(List.of(
                                        Direction.builder()
                                                .id(1L)
                                                .name("Direction 2")
                                                .description("Description Direction 2")
                                                .build(),
                                        Direction.builder()
                                                .id(2L)
                                                .name("Direction 2")
                                                .description("Description 2")
                                                .build()))
                                .build())
                        .date(LocalDate.now())
                        .score(10)
                        .candidate(Candidate.builder()
                                .id(2L)
                                .firstName("First Name 2")
                                .lastName("Last Name 2")
                                .fatherName("Father Name 2")
                                .description("Description 2")
                                .directions(List.of(
                                        Direction.builder()
                                                .id(2L)
                                                .name("Direction 2")
                                                .description("Description Direction 2")
                                                .build()
                                ))
                                .build())
                        .build()
        );
    }

    public static List<TestResult> createLisOfTestResultsWithId() {
        List<TestResult> testResults = createLisOfTestResults();
        testResults.get(0).setId(1L);
        testResults.get(1).setId(2L);
        return testResults;
    }

    public static List<TestResultResponseDto> createListOfTestResultResponseDto() {
        return List.of(
                TestResultResponseDto.builder()
                        .id(1L)
                        .date(LocalDate.now())
                        .score(10)
                        .candidateId(1L)
                        .testId(1L)
                        .build(),
                TestResultResponseDto.builder()
                        .id(2L)
                        .date(LocalDate.now())
                        .score(10)
                        .candidateId(2L)
                        .testId(2L)
                        .build()
        );
    }

    public static List<SortTestResultFields> createListOfSortTestResultFields() {
        return List.of(
                SortTestResultFields.SCORE,
                SortTestResultFields.CANDIDATE_FIRST_NAME
        );
    }

    public static TestResultRequestFilter createTestResultFilter() {
        return TestResultRequestFilter.builder()
                .score(10)
                .build();
    }

    public static String getInvalidJson() {
        return "";
    }

    public static TestResultRequestDto createInvalidRequestDtoNotFoundTestId() {
        return TestResultRequestDto.builder()
                .testId(423L)
                .candidateId(1L)
                .score(10)
                .date(LocalDate.now())
                .build();
    }

    public static TestResultRequestDto createInvalidRequestDtoNotFoundCandidateId() {
        return TestResultRequestDto.builder()
                .score(10)
                .candidateId(4241L)
                .testId(3L)
                .date(LocalDate.now())
                .build();
    }
}
