package org.example.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.PostgreSQLTestContainerExtension;
import org.example.persistence.repository.DirectionRepository;
import org.example.persistence.repository.TestRepository;
import org.example.service.dto.DirectionResponseDto;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.example.util.DirectionTestData.DIRECTION_URL;
import static org.example.util.TestTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@Slf4j
@Transactional
@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private DirectionRepository directionRepository;

    @Test
    public void shouldReturnIsCreatedWithoutDirectionsId() {
        TestRequestDto testRequestDto = createRequestTestWithoutDirections();

        HttpEntity<TestRequestDto> request = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                TEST_URL,
                POST,
                request,
                TestResponseDto.class
        );

        TestResponseDto addedTest = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testRequestDto.name(), addedTest.name());
        assertEquals(testRequestDto.description(), addedTest.description());
    }


    @Test
    public void shouldReturnIsCreatedWithDirectionId() {
        TestRequestDto testRequestDto = createRequestTestWithDirections();

        HttpEntity<TestRequestDto> request = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                TEST_URL,
                POST,
                request,
                TestResponseDto.class
        );

        TestResponseDto addedTest = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testRequestDto.name(), addedTest.name());
        assertEquals(testRequestDto.description(), addedTest.description());
        assertEquals(testRequestDto.directionsId().size(), addedTest.directions().size());
    }



    @Test
    public void shouldReturnBadRequestWhenCreating() {
        TestRequestDto testRequestDto = createInvalidRequest();

        HttpEntity<TestRequestDto> requestEntity = new HttpEntity<>(testRequestDto);

        ResponseEntity<DirectionResponseDto> response = restTemplate.exchange(
                DIRECTION_URL,
                POST,
                requestEntity,
                DirectionResponseDto.class
        );

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }
}
