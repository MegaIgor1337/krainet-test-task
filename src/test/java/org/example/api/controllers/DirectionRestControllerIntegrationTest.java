package org.example.api.controllers;

import org.example.PostgreSQLTestContainerExtension;
import org.example.persistence.repository.TestRepository;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.example.util.DirectionTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Sql(scripts = "classpath:testdata/add_tests_test_data.sql", executionPhase = BEFORE_TEST_METHOD)
@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DirectionRestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestRepository testRepository;

    @Test
    public void shouldReturnIsCreatedWithoutTestId() {
        DirectionRequestDto directionRequestDto = createRequestDirection();

        HttpEntity<DirectionRequestDto> request = new HttpEntity<>(directionRequestDto);

        ResponseEntity<DirectionResponseDto> response = restTemplate.exchange(
                DIRECTION_URL,
                POST,
                request,
                DirectionResponseDto.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        DirectionResponseDto addedDirection = response.getBody();
        assertEquals(directionRequestDto.name(), addedDirection.name());
        assertEquals(directionRequestDto.description(), addedDirection.description());
    }

    @Test
    public void shouldReturnIsCreatedWithTestId() {
        DirectionRequestDto directionRequestDto = createRequestDirectionWithTestId();

        HttpEntity<DirectionRequestDto> request = new HttpEntity<>(directionRequestDto);

        ResponseEntity<DirectionResponseDto> response = restTemplate.exchange(
                DIRECTION_URL,
                POST,
                request,
                DirectionResponseDto.class
        );


        assertEquals(1, testRepository.findTestByTestIdAndDirectionName(directionRequestDto.test_id(),
                directionRequestDto.name()).getDirections().size());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());


        DirectionResponseDto addedDirection = response.getBody();
        assertEquals(directionRequestDto.name(), addedDirection.name());
        assertEquals(directionRequestDto.description(), addedDirection.description());
    }


    @Test
    public void shouldReturnBadRequest() {
        DirectionRequestDto directionRequestDto = createInvalidRequest();

        HttpEntity<DirectionRequestDto> requestEntity = new HttpEntity<>(directionRequestDto);

        ResponseEntity<DirectionResponseDto> response = restTemplate.exchange(
                DIRECTION_URL,
                POST,
                requestEntity,
                DirectionResponseDto.class
        );

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }
}
