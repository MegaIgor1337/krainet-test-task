package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "TestResponseDto", description = "Response dto for test")
public record TestResponseDto(
        @Schema(description = "name of the test")
        String name,
        @Schema(description = "description of the test")
        String description,
        @Schema(description = "directions of the test")
        List<DirectionResponseDto> directions
) {
}
