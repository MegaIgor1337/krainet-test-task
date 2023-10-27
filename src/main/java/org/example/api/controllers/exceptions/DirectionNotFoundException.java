package org.example.api.controllers.exceptions;

public class DirectionNotFoundException extends RuntimeException {
    public DirectionNotFoundException(String message) {
        super(message);
    }
}
