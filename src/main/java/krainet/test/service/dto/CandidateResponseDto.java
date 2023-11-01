package krainet.test.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "CandidateResponseDto", description = "Response dto for candidate")
public record CandidateResponseDto(
        @Schema(name = "Id", description = "Id of the candidate")
        Long id,
        @Schema(name = "First name", description = "First name of the candidate")
        String firstName,
        @Schema(name = "Last name", description = "Last name of the candidate")
        String lastName,
        @Schema(name = "Father's name", description = "Last name of the father of the candidate")
        String fatherName,
        @Schema(name = "Description", description = "Description of the candidate")
        String description,
        @Schema(name = "Directions", description = "Ids of directions of the candidate")
        List<Long> directionsId
) {
}
