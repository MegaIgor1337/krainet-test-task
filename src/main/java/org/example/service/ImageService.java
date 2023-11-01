package org.example.service;

import org.example.api.exceptions.ImageStorageException;
import org.example.service.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    FileDto uploadImage(MultipartFile multipartFile, Long id) throws ImageStorageException;

    byte[] getImage(Long id);


}
