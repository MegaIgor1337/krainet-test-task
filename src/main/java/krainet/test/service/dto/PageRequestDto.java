package krainet.test.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
@Schema(name = "PageRequestDto", description = "Request dto for page size and page number")
public record PageRequestDto(
        @Min(value = 0, message = "page number must not be less than 0")
        @Schema(defaultValue = "0", description = "The number of the page")
        Integer pageNumber,
        @Min(value = 1, message = "page size must not be less than 0")
        @Schema(defaultValue = "5", description = "The size of the page")
        Integer pageSize
) {
}
