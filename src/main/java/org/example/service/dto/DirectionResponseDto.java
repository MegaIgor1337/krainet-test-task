package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record DirectionResponseDto(
        @Schema(description = "Name of the IT direction")
        String name,
        @Schema(description = "Description of the IT direction")
        String description) {
}
