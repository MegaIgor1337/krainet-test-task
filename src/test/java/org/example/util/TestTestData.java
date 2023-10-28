package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.persistence.entity.Direction;
import org.example.persistence.entity.Test;
import org.example.service.dto.DirectionResponseDto;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestResponseDto;

import java.util.List;
import java.util.Set;

@UtilityClass
public class TestTestData {
    public static final String TEST_URL = "/api/v1/tests";
    public static final Long TEST_ID = 1L;
    public static final Long INVALID_ID = 100L;
    public static final String TEST_URL_PUT = String.format("/api/v1/tests/%s", TEST_ID);
    public static final String TEST_URL_PUT_INVALID_ID = String.format("/api/v1/tests/%s", INVALID_ID);


    public static Test createTestWithoutDirections() {
        return Test.builder()
                .id(1L)
                .name("Test")
                .description("Test")
                .build();
    }

    public static Test createTestWithDirections() {
        return Test.builder()
                .id(1L)
                .name("Test1")
                .description("Description1")
                .directions(
                        List.of(Direction.builder()
                                        .id(1L)
                                        .name("backend")
                                        .description("Good")
                                        .build(),
                                Direction.builder()
                                        .id(2L)
                                        .name("backend")
                                        .description("Nice")
                                        .build()
                        )
                ).build();
    }

    public static TestRequestDto createRequestTestWithoutDirections() {
        return TestRequestDto.builder()
                .name("Test1")
                .description("Description1")
                .build();
    }

    public static TestRequestDto createRequestTestWithDirections() {
        return TestRequestDto.builder()
                .name("Test1")
                .description("Description1")
                .directionsId(
                        Set.of(
                                1L,
                                2L
                        )
                ).build();
    }

    public static TestRequestDto createInvalidRequest() {
        return TestRequestDto.builder()
                .description("Description1")
                .directionsId(
                        Set.of(
                                1L,
                                2L
                        )
                ).build();
    }

    public static String getInvalidJsonRequest() {
        return "{\"invalid_field\":123}";
    }

    public static String getJsonWithoutObligatoryParam() {
        return "{\"name\":\"\"}";
    }

    public static TestRequestDto createRequestTestDtoWithNotExistedDirectionsId() {
        return TestRequestDto.builder()
                .name("Test1")
                .description("Description1")
                .directionsId(
                        Set.of(
                                10L,
                                20L
                        )
                ).build();
    }

    public static Test createTestWithoutId() {
        return Test.builder()
                .name("Test1")
                .description("Description1")
                .build();
    }

    public static Test createAddedTestWithId() {
        return Test.builder()
                .id(1L)
                .name("Test1")
                .description("Description1")
                .build();
    }

    public static TestResponseDto createResponseTestDtoWithoutDirections() {
        return TestResponseDto.builder()
                .name("Test1")
                .description("Description1")
                .build();
    }

    public static Test createTestWithoutIdWithDirections() {
        return Test.builder()
                .name("Test1")
                .description("Description1")
                .directions(
                        List.of(Direction.builder()
                                        .id(1L)
                                        .name("backend")
                                        .description("Good")
                                        .build(),
                                Direction.builder()
                                        .id(2L)
                                        .name("backend")
                                        .description("Nice")
                                        .build()
                        )
                ).build();
    }

    public TestResponseDto createResponseTestDtoWithDirections() {
        return TestResponseDto.builder()
                .name("Test1")
                .description("Description1")
                .directions(
                        List.of(
                                DirectionResponseDto.builder()
                                        .name("backend")
                                        .description("Good")
                                        .build(),
                                DirectionResponseDto.builder()
                                        .name("backend")
                                        .description("Nice")
                                        .build()
                        )
                ).build();
    }

    public static TestRequestDto createRequestTestWithInvalidDirections() {
        return TestRequestDto.builder()
                .name("Test1")
                .description("Description1")
                .directionsId(
                        Set.of(
                                100L,
                                200L
                        )
                ).build();
    }

}
