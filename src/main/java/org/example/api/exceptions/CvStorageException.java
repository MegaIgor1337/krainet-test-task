package org.example.api.exceptions;

import java.io.IOException;

public class CvStorageException extends IOException {
    public CvStorageException(String message) {
        super(message);
    }
}
