package org.example.api.controllers;

import org.example.PostgreSQLTestContainerExtension;
import org.example.persistence.repository.DirectionRepository;
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
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static org.example.util.DirectionTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(scripts = "classpath:testdata/add_tests_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_tests_test_data.sql", executionPhase = AFTER_TEST_METHOD)})
@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DirectionRestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private DirectionRepository directionRepository;

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

        DirectionResponseDto addedDirection = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
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

        DirectionResponseDto addedDirection = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(directionRequestDto.name(), addedDirection.name());
        assertEquals(directionRequestDto.description(), addedDirection.description());
    }


    @Test
    public void shouldReturnBadRequestWhenCreating() {
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

    @Test
    public void shouldReturnCreatedWhenUpdateWithoutTest() {
        DirectionRequestDto directionRequestDto = createRequestForUpdateDirectionWithoutTest();

        HttpEntity<DirectionRequestDto> requestEntity = new HttpEntity<>(directionRequestDto);

        ResponseEntity<DirectionResponseDto> response = restTemplate.exchange(
                DIRECTION_URL_POST,
                PUT,
                requestEntity,
                DirectionResponseDto.class
        );

        DirectionResponseDto directionResponseDto = response.getBody();

        assertEquals(directionRequestDto.name(), directionResponseDto.name());
        assertEquals(directionRequestDto.description(), directionResponseDto.description());
        assertEquals(CREATED, response.getStatusCode());
    }

    @Test
    public void shouldReturnCreatedWhenUpdateWithTest() {
        DirectionRequestDto directionRequestDto = createRequestForUpdateDirectionWithTest();

        HttpEntity<DirectionRequestDto> requestEntity = new HttpEntity<>(directionRequestDto);

        ResponseEntity<DirectionResponseDto> response = restTemplate.exchange(
                DIRECTION_URL_POST,
                PUT,
                requestEntity,
                DirectionResponseDto.class
        );

        DirectionResponseDto directionResponseDto = response.getBody();
        String testNameFromDb = directionRepository.findTestNameByDirectionId(DIRECTION_ID);

        assertEquals(testRepository.findById(directionRequestDto.testId()).get().getName(),
                testNameFromDb);
        assertEquals(directionRequestDto.name(), directionResponseDto.name());
        assertEquals(directionRequestDto.description(), directionResponseDto.description());
        assertEquals(CREATED, response.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundWhenTestIdIsNotExist() {
        DirectionRequestDto directionRequestDto = createInvalidRequestDirectionDtoWithNotExistedTestId();

        HttpEntity<DirectionRequestDto> requestEntity = new HttpEntity<>(directionRequestDto);

        ResponseEntity<DirectionResponseDto> response = restTemplate.exchange(
                DIRECTION_URL_POST,
                PUT,
                requestEntity,
                DirectionResponseDto.class
        );

        assertEquals(NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void shouldReturnNotFoundWhenDirectionIdIsNotExist() {
        DirectionRequestDto directionRequestDto = createRequestDirectionWithTest();

        HttpEntity<DirectionRequestDto> requestEntity = new HttpEntity<>(directionRequestDto);

        ResponseEntity<DirectionResponseDto> response = restTemplate.exchange(
                DIRECTION_INVALID_ID_URL_POST,
                PUT,
                requestEntity,
                DirectionResponseDto.class
        );
        System.out.println(testRepository.findAll());

        assertEquals(NOT_FOUND, response.getStatusCode());
    }
}
