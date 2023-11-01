package org.example.api.exceptions;

public class WrongMediaTypeException extends RuntimeException {
    public WrongMediaTypeException(String message) {
        super(message);
    }
}
