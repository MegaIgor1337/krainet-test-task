package krainet.test.util;

import krainet.test.persistence.entity.File;
import krainet.test.service.dto.FileDto;
import krainet.test.service.util.ValidationConstants;
import lombok.experimental.UtilityClass;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class FileTestData {
    public static final Long CANDIDATE_ID = 1L;
    public static final Long INVALID_CANDIDATE_ID = 14L;
    private static final String PATH_TO_IMAGE = "/images/test-image.png";
    private static final String PATH_TO_PDF_FILE = "/docks/test-pdf-file.pdf";
    public static final String IMAGE_URL_UPLOAD = String.format("/api/v1/candidates/%s/image", CANDIDATE_ID);
    public static final String IMAGE_URL_UPLOAD_INVALID = String
            .format("/api/v1/candidates/%s/image", INVALID_CANDIDATE_ID);
    public static final String СМ_URL_UPLOAD_INVALID = String
            .format("/api/v1/candidates/%s/image", INVALID_CANDIDATE_ID);
    public static final String CV_URL_UPLOAD = String.format("/api/v1/candidates/%s/cv", CANDIDATE_ID);

    public static File getImage() throws IOException {
        InputStream imageStream = FileTestData.class.getResourceAsStream(PATH_TO_IMAGE);
        return new File(1L, "test-image.png", IOUtils.toByteArray(imageStream));
    }

    public static File getDocument() throws IOException {
        InputStream documentStream = FileTestData.class.getResourceAsStream(PATH_TO_PDF_FILE);
        return new File(2L, "test-pdf-file.pdf", IOUtils.toByteArray(documentStream));
    }

    public static MockMultipartFile getRightMultipartImage() throws IOException {
        InputStream documentStream = FileTestData.class.getResourceAsStream(PATH_TO_IMAGE);
        return new MockMultipartFile("image", "test-image-file.png",
                ValidationConstants.IMAGE_PNG_VALUE, IOUtils.toByteArray(documentStream));
    }

    public static MockMultipartFile getBadMultipartImage() throws IOException {
        InputStream documentStream = FileTestData.class.getResourceAsStream(PATH_TO_IMAGE);
        return new MockMultipartFile("test-image-file", "test-image-file.png",
                ValidationConstants.DOCUMENT_PDF_VALUE, IOUtils.toByteArray(documentStream));
    }

    public static MockMultipartFile getRightMultipartPdfFile() throws IOException {
        InputStream documentStream = FileTestData.class.getResourceAsStream(PATH_TO_PDF_FILE);
        return new MockMultipartFile("cv", "test-pdf-file.pdf",
                ValidationConstants.DOCUMENT_PDF_VALUE, IOUtils.toByteArray(documentStream));
    }

    public static MockMultipartFile getBadMultipartPdfFile() throws IOException {
        InputStream documentStream = FileTestData.class.getResourceAsStream(PATH_TO_PDF_FILE);
        return new MockMultipartFile("test-pdf-file", "test-pdf-file.pdf",
                ValidationConstants.IMAGE_PNG_VALUE, IOUtils.toByteArray(documentStream));
    }

    public static MultipartFile getEmptyPdfFile() throws IOException {
        return new MockMultipartFile("test-pdf-file", "test-pdf-file.pdf",
                ValidationConstants.DOCUMENT_PDF_VALUE, new byte[0]);

    }

    public static MultipartFile getEmptyImage() throws IOException {
        return new MockMultipartFile("test-image-file", "test-image-file.png",
                ValidationConstants.IMAGE_PNG_VALUE, new byte[0]);

    }

    public static FileDto getFileDtoImage() {
        return new FileDto(1L, "test-image-file.png");
    }

    public static FileDto getFileDtoCv() {
        return new FileDto(1L, "test-pdf-file.pdf");
    }
}
