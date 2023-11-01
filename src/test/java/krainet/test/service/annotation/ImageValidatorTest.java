package krainet.test.service.annotation;

import krainet.test.api.exceptions.EmptyFileException;
import krainet.test.api.exceptions.WrongMediaTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static krainet.test.util.FileTestData.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ImageValidatorTest {
    @InjectMocks
    private ImageValidator imageValidator;

    @Test
    public void shouldReturnTrueWhenImageIsCorrect() throws IOException {
        MultipartFile image = getRightMultipartImage();

        boolean result = imageValidator.isValid(image, null);

        assertTrue(result);
    }

    @Test
    public void shouldReturnExceptionIfTypeIsNotRightWhenCvIsCorrect() throws IOException {
        MultipartFile image = getBadMultipartImage();

        assertThrows(WrongMediaTypeException.class, () -> {
            imageValidator.isValid(image, null);
        });
    }

    @Test
    public void shouldReturnExceptionIfImageIsEmpty() throws IOException {
        MultipartFile image = getEmptyImage();

        assertThrows(EmptyFileException.class, () -> {
            imageValidator.isValid(image, null);
        });
    }
}
