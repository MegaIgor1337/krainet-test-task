package krainet.test.service;

import krainet.test.service.dto.FileDto;
import krainet.test.api.exceptions.ImageStorageException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    FileDto uploadImage(MultipartFile multipartFile, Long id) throws ImageStorageException;

    byte[] getImage(Long id);


}
