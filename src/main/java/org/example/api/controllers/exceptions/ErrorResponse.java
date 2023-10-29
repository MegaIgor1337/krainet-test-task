package org.example.api.controllers.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponse(
        @Schema(description = "Code of the status")
        int statusCode,
        @Schema(description = "Message of the error")
        String message) {
}
