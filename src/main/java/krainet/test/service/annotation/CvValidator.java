package krainet.test.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import krainet.test.api.exceptions.EmptyFileException;
import krainet.test.api.exceptions.WrongMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

import static krainet.test.service.util.ValidationConstants.DOCUMENT_PDF_VALUE;

public class CvValidator implements ConstraintValidator<ValidationCv, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile document, ConstraintValidatorContext constraintValidatorContext) {
        String originalFilename = document.getOriginalFilename();
        if (!DOCUMENT_PDF_VALUE.equals(document.getContentType())) {
            throw new WrongMediaTypeException(String.format("Not right format of document %s", originalFilename));
        }
        if (document.isEmpty()) {
            throw new EmptyFileException(String.format("Failed to store empty file %s", originalFilename));
        }
        return true;
    }
}
