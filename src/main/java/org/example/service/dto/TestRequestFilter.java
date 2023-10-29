package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import org.example.service.annotation.IsDirectionExist;

import java.util.List;

@Builder
@Schema(name = "TestFilterDto", description = "Filter for tests")
public record TestRequestFilter(
        @Schema(defaultValue = "Test", description = "Name of the test for filter")
        String testName,
        @Schema(defaultValue = "[1, 4, 2]", description = "Ids of directions for filter")
        List<@IsDirectionExist
        @Min(value = 1, message = "Id of direction must not be less than 1")
                Long> directionsId
) {
}