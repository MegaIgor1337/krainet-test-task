package krainet.test.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "DirectionResponseDto", description = "Response dto for direction")
public record DirectionResponseDto(
        @Schema(name = "Id", description = "Id of the direction")
        Long id,
        @Schema(name = "Name", description = "Name of the IT direction")
        String name,
        @Schema(name = "Description", description = "Description of the IT direction")
        String description) {
}
