package org.example.api.exceptions;

public class TestNotFoundException extends RuntimeException {
    public TestNotFoundException(String message) {
        super(message);
    }
}
