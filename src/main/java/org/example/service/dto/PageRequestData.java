package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record PageRequestData(
        @Min(value = 0, message = "Number of the page can not be less than 0")
        @Schema(defaultValue = "0", description = "Number of the page")
        Integer pageNumber,
        @Min(value = 0, message = "Size of the page can not be less than 0")
        @Schema(defaultValue = "2", description = "Size of the page")
        Integer pageSize
) {
}
