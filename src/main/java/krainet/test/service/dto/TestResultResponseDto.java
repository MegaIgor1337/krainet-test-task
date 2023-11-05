package krainet.test.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@Schema(name = "TestResultResponseDto", description = "Response dto for test result")
public record TestResultResponseDto(
        @Schema(name = "Id", description = "Id of the test result")
        Long id,
        @Schema(name = "Date", description = "Date of the test")
        LocalDate date,
        @Schema(name = "Score", description = "Score of the test")
        Integer score,
        @Schema(name = "TestId", description = "Id of the test of the result")
        Long testId,
        @Schema(name = "CandidateId", description = "Id of tge candidate who did the test")
        Long candidateId
) {
}
