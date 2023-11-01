package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "TestResponseDto", description = "Response dto for test")
public record TestResponseDto(
        @Schema(name = "Id", description = "Id of the test")
        Long id,
        @Schema(name = "Name", description = "Name of the test")
        String name,
        @Schema(name = "Description", description = "Description of the test")
        String description,
        @Schema(name = "Directions", description = "Directions of the test")
        List<Long> directionsId
) {
}
