package krainet.test.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krainet.test.service.annotation.IsDirectionExist;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Builder
@Schema(name = "TestRequestDto", description = "Request dto for test")
public record TestRequestDto(
        @NotNull(message = "Name of test must not be null")
        @Length(max = 255, message = "Name is too long, the max number of symbols is 255")
        @Schema(defaultValue = "Test Name", description = "Name of the Test")
        String name,
        @NotNull(message = "Description of test must not be null")
        @Length(max = 255, message = "Description is too long, the max number of symbols is 255")
        @Schema(defaultValue = "Test Description", description = "Description of the test")
        String description,
        @Schema(defaultValue = "[1, 2]", description = "Id of directions for test")
        Set<@Min(value = 1, message = "Id of direction must not be less than 1")
        @IsDirectionExist Long> directionsId
) {
}
