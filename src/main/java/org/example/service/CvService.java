package org.example.service;

import org.example.api.exceptions.CvStorageException;
import org.example.service.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

public interface CvService {
    FileDto uploadCv(MultipartFile multipartFile, Long id) throws CvStorageException;

    byte[] getCv(Long id);

}
