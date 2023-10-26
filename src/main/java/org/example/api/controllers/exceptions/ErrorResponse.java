package org.example.api.controllers.exceptions;

public record ErrorResponse(int statusCode, String message) {
}
