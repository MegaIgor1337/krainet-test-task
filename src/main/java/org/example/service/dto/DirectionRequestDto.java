package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.example.service.annotation.IsTestExist;
import org.hibernate.validator.constraints.Length;

@Builder
public record DirectionRequestDto(
        @NotNull(message = "Name of direction must not be null")
        @Length(max = 255, message = "Name is too long, the max number of symbols is 255")
        @Schema(defaultValue = "Direction Name", description = "Name of the IT direction")
        String name,
        @NotNull(message = "Description of direction must not be null")
        @Length(max = 255, message = "Description is too long, the max number of symbols is 255")
        @Schema(defaultValue = "Direction Description", description = "Description of the IT direction")
        String description,
        @IsTestExist
        @Min(value = 1, message = "Id must not be less than 1")
        @Schema(defaultValue = "1", description = "Id of the test")
        Long test_id) {
}
