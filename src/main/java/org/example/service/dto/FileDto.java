package org.example.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "FileDto", description = "Response dto for image")
public record FileDto(
        @Schema(name = "Id", description = "Id of the image")
        Long id,
        @Schema(name = "name", description = "The file name of the image")
        String name
) {
}
