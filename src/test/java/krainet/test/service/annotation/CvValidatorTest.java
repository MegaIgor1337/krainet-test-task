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
public class CvValidatorTest {
    @InjectMocks
    private CvValidator cvValidator;

    @Test
    public void shouldReturnTrueWhenCvIsCorrect() throws IOException {
        MultipartFile pdfFile = getRightMultipartPdfFile();

        boolean result = cvValidator.isValid(pdfFile, null);

        assertTrue(result);
    }

    @Test
    public void shouldReturnExceptionIfTypeIsNotRightWhenCvIsCorrect() throws IOException {
        MultipartFile pdfFile = getBadMultipartPdfFile();

        assertThrows(WrongMediaTypeException.class, () -> {
            cvValidator.isValid(pdfFile, null);
        });
    }

    @Test
    public void shouldReturnExceptionIfImageIsEmpty() throws IOException {
        MultipartFile pdfFile = getEmptyPdfFile();

        assertThrows(EmptyFileException.class, () -> {
            cvValidator.isValid(pdfFile, null);
        });
    }
}
