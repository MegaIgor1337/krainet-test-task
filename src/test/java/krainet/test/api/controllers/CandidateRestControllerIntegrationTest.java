package krainet.test.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import krainet.test.PostgreSQLTestContainerExtension;
import krainet.test.service.dto.CandidateRequestDto;
import krainet.test.service.dto.CandidateResponseDto;
import krainet.test.util.CandidateTestData;
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

import static krainet.test.util.CandidateTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(scripts = "classpath:testdata/clear_candidate_direction_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_directions_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_candidates_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_directions_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_candidates_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_candidate_direction_test_data.sql", executionPhase = BEFORE_TEST_METHOD)
})
@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CandidateRestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnIsCreateWithDirectionsId() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CandidateTestData.CANDIDATE_URL,
                POST,
                requestEntity,
                CandidateResponseDto.class
        );

        CandidateResponseDto addedCandidate = response.getBody();

        assertNotNull(addedCandidate.id());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(candidateRequestDto.firstName(), addedCandidate.firstName());
        assertEquals(candidateRequestDto.lastName(), addedCandidate.lastName());
        assertEquals(candidateRequestDto.fatherName(), addedCandidate.fatherName());
        assertEquals(candidateRequestDto.description(), addedCandidate.description());
        assertEquals(candidateRequestDto.directionsId().size(), addedCandidate.directionsId().size());
    }

    @Test
    public void shouldReturnIsCreatedWithoutDirectionsId() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithoutDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CandidateTestData.CANDIDATE_URL,
                POST,
                requestEntity,
                CandidateResponseDto.class
        );

        CandidateResponseDto addedCandidate = response.getBody();

        assertNotNull(addedCandidate.id());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(candidateRequestDto.firstName(), addedCandidate.firstName());
        assertEquals(candidateRequestDto.lastName(), addedCandidate.lastName());
        assertEquals(candidateRequestDto.fatherName(), addedCandidate.fatherName());
        assertEquals(candidateRequestDto.description(), addedCandidate.description());
    }

    @Test
    public void shouldReturnBadRequestWithoutWithDirectionsId() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithInvalidDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CandidateTestData.CANDIDATE_URL,
                POST,
                requestEntity,
                CandidateResponseDto.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void shouldReturnIsCreateWithDirectionsIdWhenUpdate() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CandidateTestData.CANDIDATE_URL_PUT,
                PUT,
                requestEntity,
                CandidateResponseDto.class
        );

        CandidateResponseDto addedCandidate = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(CandidateTestData.ID, addedCandidate.id());
        assertEquals(candidateRequestDto.firstName(), addedCandidate.firstName());
        assertEquals(candidateRequestDto.lastName(), addedCandidate.lastName());
        assertEquals(candidateRequestDto.fatherName(), addedCandidate.fatherName());
        assertEquals(candidateRequestDto.description(), addedCandidate.description());
        assertEquals(candidateRequestDto.directionsId().size(), addedCandidate.directionsId().size());
    }

    @Test
    public void shouldReturnBadRequestIfTestIdNotExist() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithoutDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CandidateTestData.CANDIDATE_URL_PUT,
                PUT,
                requestEntity,
                CandidateResponseDto.class
        );

        CandidateResponseDto addedCandidate = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(CandidateTestData.ID, addedCandidate.id());
        assertEquals(candidateRequestDto.firstName(), addedCandidate.firstName());
        assertEquals(candidateRequestDto.lastName(), addedCandidate.lastName());
        assertEquals(candidateRequestDto.fatherName(), addedCandidate.fatherName());
        assertEquals(candidateRequestDto.description(), addedCandidate.description());
    }

    @Test
    public void shouldReturnNotFoundWhenIdInvalid() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithoutDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CandidateTestData.CANDIDATE_URL_INVALID_PUT,
                PUT,
                requestEntity,
                CandidateResponseDto.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenGetWithPageRequestData() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                CANDIDATE_URL_GET_PAGE,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<CandidateResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(2, responseBody.size());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenParamsAreNotExist() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                CANDIDATE_URL,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<CandidateResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(5, responseBody.size());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenFilterByName() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                CANDIDATE_URL_GET_NAME,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<CandidateResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(CANDIDATE_NAME, responseBody.get(0).firstName());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenAllParamsAreExist() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                CANDIDATE_URL_FILTER_AND_PAGE,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<CandidateResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });
        assertNotNull(responseBody);

        assertEquals(CANDIDATE_NAME, responseBody.get(0).firstName());
        assertEquals(CANDIDATE_LAST_NAME, responseBody.get(0).lastName());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenSorting() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                CANDIDATE_URL_GET_SORTING,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<CandidateResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });
        assertNotNull(responseBody);

        assertEquals(SORTED_CANDIDATE_NAME, responseBody.get(0).firstName());
        assertEquals(OK, response.getStatusCode());
    }

}
