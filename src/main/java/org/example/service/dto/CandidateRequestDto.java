package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.example.service.annotation.IsDirectionExist;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Builder
@Schema(name = "CandidateRequestDto", description = "Request dto for candidate")
public record CandidateRequestDto(
        @NotNull(message = "First name of candidate must not be null")
        @Length(max = 50, message = "First name is too long, the max number of symbols is 255")
        @Schema(defaultValue = "Candidate first Name", description = "First name of the candidate")
        String firstName,
        @NotNull(message = "Last name of candidate must not be null")
        @Length(max = 50, message = "Last name is too long, the max number of symbols is 255")
        @Schema(defaultValue = "Candidate last Name", description = "Last name of the candidate")
        String lastName,
        @Length(max = 50, message = "Father name is too long, the max number of symbols is 255")
        @Schema(defaultValue = "Candidate father Name", description = "Father name of the candidate")
        String fatherName,
        @Length(max = 255, message = "Father name is too long, the max number of symbols is 255")
        @Schema(defaultValue = "Candidate father Name", description = "Father name of the candidate")
        String description,
        @Schema(defaultValue = "[1, 2]", description = "Id of directions for test")
        Set<@Min(value = 1, message = "Id of direction must not be less than 1")
        @IsDirectionExist Long> directionsId
) {
}
