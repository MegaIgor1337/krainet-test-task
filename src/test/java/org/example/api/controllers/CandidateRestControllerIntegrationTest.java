package org.example.api.controllers;

import org.example.PostgreSQLTestContainerExtension;
import org.example.service.dto.CandidateRequestDto;
import org.example.service.dto.CandidateResponseDto;
import org.example.util.CandidateTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.example.util.CandidateTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
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

    @Test
    public void shouldReturnIsCreateWithDirectionsId() {
        CandidateRequestDto candidateRequestDto = CandidateTestData.createRequestDtoWithDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CANDIDATE_URL,
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
        CandidateRequestDto candidateRequestDto = createRequestDtoWithoutDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CANDIDATE_URL,
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
        CandidateRequestDto candidateRequestDto = createRequestDtoWithInvalidDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CANDIDATE_URL,
                POST,
                requestEntity,
                CandidateResponseDto.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void shouldReturnIsCreateWithDirectionsIdWhenUpdate() {
        CandidateRequestDto candidateRequestDto = createRequestDtoWithDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CANDIDATE_URL_PUT,
                PUT,
                requestEntity,
                CandidateResponseDto.class
        );

        CandidateResponseDto addedCandidate = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ID, addedCandidate.id());
        assertEquals(candidateRequestDto.firstName(), addedCandidate.firstName());
        assertEquals(candidateRequestDto.lastName(), addedCandidate.lastName());
        assertEquals(candidateRequestDto.fatherName(), addedCandidate.fatherName());
        assertEquals(candidateRequestDto.description(), addedCandidate.description());
        assertEquals(candidateRequestDto.directionsId().size(), addedCandidate.directionsId().size());
    }

    @Test
    public void shouldReturnIsCreateWithoutDirectionsIdWhenUpdate() {
        CandidateRequestDto candidateRequestDto = createRequestDtoWithoutDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CANDIDATE_URL_PUT,
                PUT,
                requestEntity,
                CandidateResponseDto.class
        );

        CandidateResponseDto addedCandidate = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ID, addedCandidate.id());
        assertEquals(candidateRequestDto.firstName(), addedCandidate.firstName());
        assertEquals(candidateRequestDto.lastName(), addedCandidate.lastName());
        assertEquals(candidateRequestDto.fatherName(), addedCandidate.fatherName());
        assertEquals(candidateRequestDto.description(), addedCandidate.description());
    }

    @Test
    public void shouldReturnNotFoundWhenIdInvalid() {
        CandidateRequestDto candidateRequestDto = createRequestDtoWithoutDirectionsId();

        HttpEntity<CandidateRequestDto> requestEntity = new HttpEntity<>(candidateRequestDto);

        ResponseEntity<CandidateResponseDto> response = restTemplate.exchange(
                CANDIDATE_URL_INVALID_PUT,
                PUT,
                requestEntity,
                CandidateResponseDto.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
