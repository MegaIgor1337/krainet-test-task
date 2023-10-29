package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "DirectionResponseDto", description = "Response dto dor direction")
public record DirectionResponseDto(
        @Schema(description = "Name of the IT direction")
        String name,
        @Schema(description = "Description of the IT direction")
        String description) {
}
