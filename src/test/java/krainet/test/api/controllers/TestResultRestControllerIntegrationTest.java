package krainet.test.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import krainet.test.PostgreSQLTestContainerExtension;
import krainet.test.service.dto.TestResultRequestDto;
import krainet.test.service.dto.TestResultResponseDto;
import krainet.test.util.TestResultTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.io.IOException;
import java.util.List;

import static krainet.test.util.TestResultTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(scripts = "classpath:testdata/clear_candidate_test_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_tests_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_candidates_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_candidates_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_tests_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_candidate_test_test_data.sql", executionPhase = BEFORE_TEST_METHOD)
})
@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResultRestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnIsCreateWhenSaving() {
        TestResultRequestDto testResultRequestDto = createTestResultRequestDto();

        HttpEntity<TestResultRequestDto> requestEntity = new HttpEntity<>(testResultRequestDto);

        ResponseEntity<TestResultResponseDto> response = restTemplate.exchange(
                TestResultTestData.TEST_RESULT_URL,
                POST,
                requestEntity,
                TestResultResponseDto.class
        );

        TestResultResponseDto addedTestResult = response.getBody();

        assertNotNull(addedTestResult.id());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testResultRequestDto.score(), addedTestResult.score());
        assertEquals(testResultRequestDto.testId(), addedTestResult.testId());
        assertEquals(testResultRequestDto.candidateId(), addedTestResult.candidateId());
    }

    @Test
    public void shouldReturnNotFountTestId() {
        TestResultRequestDto testResultRequestDto = TestResultTestData.createInvalidRequestDtoNotFoundTestId();

        HttpEntity<TestResultRequestDto> requestEntity = new HttpEntity<>(testResultRequestDto);

        ResponseEntity<TestResultResponseDto> response = restTemplate.exchange(
                TestResultTestData.TEST_RESULT_URL,
                POST,
                requestEntity,
                TestResultResponseDto.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturnNotFountCandidateId() {
        TestResultRequestDto testResultRequestDto = createInvalidRequestDtoNotFoundCandidateId();

        HttpEntity<TestResultRequestDto> requestEntity = new HttpEntity<>(testResultRequestDto);

        ResponseEntity<TestResultResponseDto> response = restTemplate.exchange(
                TestResultTestData.TEST_RESULT_URL,
                POST,
                requestEntity,
                TestResultResponseDto.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturnIsCreatedWhenUpdating() {
        TestResultRequestDto testResultRequestDto = TestResultTestData.createTestResultRequestDto();

        HttpEntity<TestResultRequestDto> requestEntity = new HttpEntity<>(testResultRequestDto);

        ResponseEntity<TestResultResponseDto> response = restTemplate.exchange(
                TestResultTestData.TEST_RESULT_URL_PUT,
                PUT,
                requestEntity,
                TestResultResponseDto.class
        );

        TestResultResponseDto addedCandidate = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(TestResultTestData.TEST_RESULT_ID, addedCandidate.id());
        assertEquals(testResultRequestDto.testId(), addedCandidate.testId());
        assertEquals(testResultRequestDto.candidateId(), addedCandidate.candidateId());
    }

    @Test
    public void shouldReturnBadRequestIfTestIdNotExist() {
        TestResultRequestDto requestDto = TestResultTestData.createInvalidRequestDtoNotFoundTestId();

        HttpEntity<TestResultRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<TestResultResponseDto> response = restTemplate.exchange(
                TestResultTestData.TEST_RESULT_URL_PUT,
                PUT,
                requestEntity,
                TestResultResponseDto.class
        );


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundIfTestIdNotExist() {
        TestResultRequestDto requestDto = TestResultTestData.createInvalidRequestDtoNotFoundTestId();

        HttpEntity<TestResultRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<TestResultResponseDto> response = restTemplate.exchange(
                TestResultTestData.TEST_RESULT_URL_PUT_INVALID,
                PUT,
                requestEntity,
                TestResultResponseDto.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenGetWithPageRequestData() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TEST_RESULT_URL_GET_PAGE,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResultResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(1, responseBody.size());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenParamsAreNotExist() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TEST_RESULT_URL,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResultResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(4, responseBody.size());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenFilterAndPage() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TEST_RESULT_URL_FILTER_AND_PAGE,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResultResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertNotNull(responseBody);
        assertTrue(responseBody.isEmpty());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenAllParamsAreExist() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TEST_RESULT_URL_ALL_PARAMS,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResultResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertNotNull(responseBody);
        assertTrue(responseBody.isEmpty());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenSorting() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TEST_RESULT_URL_GET_SORTING,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResultResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertNotNull(responseBody);

        assertEquals(4L, responseBody.get(0).candidateId());
        assertEquals(OK, response.getStatusCode());
    }

}
