package krainet.test.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import krainet.test.service.annotation.IsCandidateExist;
import krainet.test.service.annotation.IsTestExist;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
@Schema(name = "TestResultFilter", description = "Filter for test result")
public record TestResultRequestFilter(
        @Schema(name = "Id", defaultValue = "1")
        Long id,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Schema(name = "date", defaultValue = "2023-11-03")
        LocalDate localDate,
        @Schema(name = "score", defaultValue = "10")
        Integer score,
        @Schema(name = "testId", defaultValue = "[1]")
        List<@IsTestExist Long> testId,
        @Schema(name = "candidateId", defaultValue = "[2]")
        List<@IsCandidateExist Long> candidateId

) {

}
