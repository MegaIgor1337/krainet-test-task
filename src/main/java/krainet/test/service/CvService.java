package krainet.test.service;

import krainet.test.api.exceptions.CvStorageException;
import krainet.test.service.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

public interface CvService {
    FileDto uploadCv(MultipartFile multipartFile, Long id) throws CvStorageException;

    byte[] getCv(Long id);
}
