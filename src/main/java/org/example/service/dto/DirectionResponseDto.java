package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder

public record DirectionResponseDto(
        @Schema(defaultValue = "Direction Name", description = "Name of the IT direction")
        String name,
        @Schema(defaultValue = "Direction Description", description = "Description of the IT direction")
        String description) {
}
