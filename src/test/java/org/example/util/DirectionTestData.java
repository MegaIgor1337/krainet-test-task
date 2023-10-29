package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.persistence.entity.Direction;
import org.example.persistence.entity.Test;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;

import java.util.List;

@UtilityClass
public class DirectionTestData {
    public static final String DIRECTION_URL = "/api/v1/directions";
    public static final Long DIRECTION_ID = 1L;
    public static final Integer PAGE_NUMBER = 1;
    public static final Integer PAGE_SIZE = 3;
    public static final Long INVALID_DIRECTION_ID = 20L;
    public static final String DIRECTION_NAME = "backend";
    public static final String DIRECTION_URL_GET_PAGE = String
            .format("%s?pageNumber=%s&pageSize=%s", DIRECTION_URL, PAGE_NUMBER, PAGE_SIZE);
    public static final String DIRECTION_URL_GET_NAME = String
            .format("%s?directionName=%s", DIRECTION_URL, DIRECTION_NAME);
    public static final String DIRECTION_URL_ALL_PARAMS = String
            .format("%s?directionName=%s&pageNumber=%s&pageSize=%s", DIRECTION_URL,
                    DIRECTION_NAME, PAGE_NUMBER, PAGE_SIZE);
    public static final String DIRECTION_URL_POST = String.format("%s/%s", DIRECTION_URL, DIRECTION_ID);
    public static final String DIRECTION_INVALID_ID_URL_POST = String.format("%s/%s", DIRECTION_URL,
            INVALID_DIRECTION_ID);

    public static DirectionRequestDto createRequestDirection() {
        return DirectionRequestDto.builder()
                .name("Test Direction")
                .description("Nice Direction")
                .build();
    }

    public static DirectionRequestDto createRequestDirectionWithTest() {
        return DirectionRequestDto.builder()
                .name("Test Direction")
                .description("Nice Direction")
                .testId(1L)
                .build();
    }

    public static DirectionResponseDto createResponseDirection() {
        return DirectionResponseDto.builder()
                .name("Test Direction")
                .description("Nice Direction")
                .build();
    }

    public static String getInvalidJsonRequest() {
        return "{\"invalid_field\":123}";
    }

    public static String getJsonWithoutObligatoryParam() {
        return "{\"name\":\"\"}";
    }

    public static DirectionRequestDto createInvalidRequest() {
        return DirectionRequestDto.builder()
                .description("Description")
                .build();
    }

    public static Direction createDirectionWithoutId() {
        return Direction.builder()
                .name("Test Direction")
                .description("Nice Direction")
                .build();
    }

    public static Direction createDirectionWithoutIdWithTest(Test test) {
        return Direction.builder()
                .name("Test Direction")
                .description("Nice Direction")
                .test(test)
                .build();
    }

    public static Direction createAddedDirectionWithId() {
        return Direction.builder()
                .id(1L)
                .name("Test Direction")
                .description("Nice Direction")
                .build();
    }


    public static DirectionRequestDto createRequestForUpdateDirectionWithTest() {
        return DirectionRequestDto.builder()
                .name("Update Test Direction")
                .description("Update Nice Direction")
                .testId(1L)
                .build();
    }

    public static DirectionRequestDto createRequestForUpdateDirectionWithoutTest() {
        return DirectionRequestDto.builder()
                .name("Update Test Direction")
                .description("Update Nice Direction")
                .build();
    }

    public static Direction createUpdatedDIrection(Test test) {
        return Direction.builder()
                .id(1L)
                .name("Test Direction")
                .description("Nice Direction")
                .test(test)
                .build();
    }

    public static DirectionRequestDto createRequestDirectionWithTestId() {
        return DirectionRequestDto.builder()
                .name("Test Direction")
                .description("Test Direction")
                .testId(1L)
                .build();
    }

    public static DirectionResponseDto createResponseDirectionAfterUpdate() {
        return DirectionResponseDto.builder()
                .name("Update Test Direction")
                .description("Update Nice Direction")
                .build();
    }

    public static DirectionRequestDto createInvalidRequestDirectionDtoWithNotExistedTestId() {
        return DirectionRequestDto.builder()
                .name("Test Direction")
                .description("Nice Direction")
                .testId(20L)
                .build();
    }

    public static List<DirectionResponseDto> createListOfDirectionResponseDto() {
        return List.of(
                DirectionResponseDto.builder()
                        .name(DIRECTION_NAME)
                        .description("Good")
                        .build(),
                DirectionResponseDto.builder()
                        .name(DIRECTION_NAME)
                        .description("Nice")
                        .build()
        );
    }

    public static List<Direction> createListOfDirections() {
        return List.of(
                Direction.builder()
                        .id(1L)
                        .name(DIRECTION_NAME)
                        .description("Good")
                        .build(),
                Direction.builder()
                        .id(2L)
                        .name(DIRECTION_NAME)
                        .description("Nice")
                        .build()
        );
    }
}
