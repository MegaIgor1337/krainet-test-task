package krainet.test.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krainet.test.service.annotation.IsCandidateExist;
import krainet.test.service.annotation.IsTestExist;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Schema(name = "TestResultRequestDto", description = "Request dto for test result")
public record TestResultRequestDto(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Test result has to have the date")
        @Schema(defaultValue = "2023-11-02", description = "The date of the test")
        LocalDate date,
        @NotNull(message = "Test result without score can not be")
        @Min(value = 0, message = "Score can not be less than 0")
        @Schema(defaultValue = "10", description = "Score of the test")
        Integer score,
        @NotNull(message = "Test result without test can not be")
        @IsTestExist
        @Schema(defaultValue = "1", description = "The id of the test")
        Long testId,
        @NotNull(message = "Test result without candidate can not be")
        @IsCandidateExist
        @Schema(defaultValue = "1", description = "The id of the candidate")
        Long candidateId
) {
}
