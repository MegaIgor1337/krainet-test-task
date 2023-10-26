package org.example.api.controllers.exceptions;

public class TestNotFoundException extends RuntimeException {
    public TestNotFoundException(String message) {
        super(message);
    }
}
