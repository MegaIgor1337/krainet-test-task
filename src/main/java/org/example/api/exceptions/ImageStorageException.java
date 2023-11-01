package org.example.api.exceptions;

import java.io.IOException;

public class ImageStorageException extends IOException {
    public ImageStorageException(String message) {
        super(message);
    }
}
