package org.example.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.api.exceptions.EmptyFileException;
import org.example.api.exceptions.WrongMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

import static org.example.service.util.ValidationConstants.IMAGE_PNG_VALUE;

public class ImageValidator implements ConstraintValidator<ValidationImage, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
        String originalFilename = image.getOriginalFilename();
        if (image.isEmpty()) {
            throw new EmptyFileException(String.format("Failed to store empty file %s", originalFilename));
        }
        String contentType = image.getContentType();
        if (!IMAGE_PNG_VALUE.equals(contentType)) {
            throw new WrongMediaTypeException(String.format("Not an image %s", originalFilename));
        }
        return true;
    }
}
