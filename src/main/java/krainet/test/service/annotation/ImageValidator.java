package krainet.test.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import krainet.test.api.exceptions.EmptyFileException;
import krainet.test.api.exceptions.WrongMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

import static krainet.test.service.util.ValidationConstants.TYPES_OF_IMAGE;

public class ImageValidator implements ConstraintValidator<ValidationImage, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
        String originalFilename = image.getOriginalFilename();
        if (image.isEmpty()) {
            throw new EmptyFileException(String.format("Failed to store empty file %s", originalFilename));
        }
        String contentType = image.getContentType();
        if (!TYPES_OF_IMAGE.contains(contentType)) {
            throw new WrongMediaTypeException(String.format("Not an image %s", originalFilename));
        }
        return true;
    }
}
