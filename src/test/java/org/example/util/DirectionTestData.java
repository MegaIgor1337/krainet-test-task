package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.persistence.entity.Direction;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;

@UtilityClass
public class DirectionTestData {
    public static final String DIRECTION_URL = "/api/v1/directions";

    public static DirectionRequestDto createRequestDirection() {
        return DirectionRequestDto.builder()
                .name("Test Direction")
                .description("Nice Direction")
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

    public static Direction getAddedDirectionWithoutId() {
        return Direction.builder()
                .name("Test Direction")
                .description("Nice Direction")
                .build();
    }

    public static Direction getAddedDirectionWithId() {
        return Direction.builder()
                .id(1L)
                .name("Test Direction")
                .description("Nice Direction")
                .build();
    }

    public static DirectionRequestDto createRequestDirectionWithTestId() {
        return DirectionRequestDto.builder()
                .name("Test Direction")
                .description("Test Direction")
                .test_id(1L)
                .build();
    }

}
