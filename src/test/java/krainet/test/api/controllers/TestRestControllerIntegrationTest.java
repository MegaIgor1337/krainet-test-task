package krainet.test.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import krainet.test.service.dto.TestRequestDto;
import krainet.test.service.dto.TestResponseDto;
import krainet.test.util.TestTestData;
import krainet.test.PostgreSQLTestContainerExtension;
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

import static krainet.test.util.DirectionTestData.DIRECTION_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@SqlGroup({
        @Sql(scripts = "classpath:testdata/clear_directions_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_tests_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_tests_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_directions_test_data.sql", executionPhase = BEFORE_TEST_METHOD)
})
@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnIsCreatedWithoutDirectionsId() {
        TestRequestDto testRequestDto = TestTestData.createRequestTestWithoutDirections();

        HttpEntity<TestRequestDto> request = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                TestTestData.TEST_URL,
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
        TestRequestDto testRequestDto = TestTestData.createRequestTestWithDirections();

        HttpEntity<TestRequestDto> request = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                TestTestData.TEST_URL,
                POST,
                request,
                TestResponseDto.class
        );

        TestResponseDto addedTest = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testRequestDto.name(), addedTest.name());
        assertEquals(testRequestDto.description(), addedTest.description());
        assertEquals(testRequestDto.directionsId().size(), addedTest.directionsId().size());
    }


    @Test
    public void shouldReturnBadRequestWhenCreating() {
        TestRequestDto testRequestDto = TestTestData.createInvalidRequest();

        HttpEntity<TestRequestDto> requestEntity = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                DIRECTION_URL,
                POST,
                requestEntity,
                TestResponseDto.class
        );

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void shouldReturnIsCreatedWhenUpdateWithoutDirectionsId() {
        TestRequestDto testRequestDto = TestTestData.createRequestTestWithoutDirections();

        HttpEntity<TestRequestDto> request = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                TestTestData.TEST_URL_PUT,
                PUT,
                request,
                TestResponseDto.class
        );

        TestResponseDto addedTest = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testRequestDto.name(), addedTest.name());
        assertEquals(testRequestDto.description(), addedTest.description());
    }

    @Test
    public void shouldReturnIsCreatedWhenUpdateWithDirectionsId() {
        TestRequestDto testRequestDto = TestTestData.createRequestTestWithDirections();

        HttpEntity<TestRequestDto> request = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                TestTestData.TEST_URL_PUT,
                PUT,
                request,
                TestResponseDto.class
        );

        TestResponseDto addedTest = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testRequestDto.name(), addedTest.name());
        assertEquals(testRequestDto.description(), addedTest.description());
        assertEquals(testRequestDto.directionsId().size(), addedTest.directionsId().size());
    }

    @Test
    public void shouldReturnBadRequestWhenUpdatingNameIsNull() {
        TestRequestDto testRequestDto = TestTestData.createInvalidRequest();

        HttpEntity<TestRequestDto> requestEntity = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                TestTestData.TEST_URL_PUT,
                PUT,
                requestEntity,
                TestResponseDto.class
        );

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingTestIdIsNotExists() {
        TestRequestDto testRequestDto = TestTestData.createRequestTestWithoutDirections();

        HttpEntity<TestRequestDto> requestEntity = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                TestTestData.TEST_URL_PUT_INVALID_ID,
                PUT,
                requestEntity,
                TestResponseDto.class
        );

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingDirectionIdIsNotExists() {
        TestRequestDto testRequestDto = TestTestData.createRequestTestWithInvalidDirections();

        HttpEntity<TestRequestDto> requestEntity = new HttpEntity<>(testRequestDto);

        ResponseEntity<TestResponseDto> response = restTemplate.exchange(
                TestTestData.TEST_URL_PUT_INVALID_ID,
                PUT,
                requestEntity,
                TestResponseDto.class
        );

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenGetWithPageRequestData() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TestTestData.TEST_URL_PAGE_DATA,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(2, responseBody.size());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenGetWithPageSizeRequestData() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TestTestData.TEST_URL_PAGE_SIZE,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(3, responseBody.size());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenGetWithFilterNameRequestData() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TestTestData.TEST_URL_PAGE_TEST_FILTER_NAME,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(1, responseBody.size());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenGetWithFilterIdDirectionsRequestData() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TestTestData.TEST_URL_PAGE_TEST_FILTER_ID_DIRECTIONS,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(1, responseBody.size());
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void shouldReturnOkWhenGetWithAllFilterParamsRequestData() throws IOException {

        ResponseEntity<String> response = restTemplate.exchange(
                TestTestData.TEST_URL_PAGE_TEST_FILTER_ALL_PARAMS,
                GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        List<TestResponseDto> responseBody = objectMapper
                .readValue(response.getBody(), new TypeReference<>() {
                });

        assertEquals(1, responseBody.size());
        assertEquals(OK, response.getStatusCode());
    }


}