package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.exceptions.ImageNotFoundException;
import org.example.api.exceptions.ImageStorageException;
import org.example.persistence.entity.Candidate;
import org.example.persistence.entity.File;
import org.example.persistence.repository.CandidateRepository;
import org.example.persistence.repository.FileRepository;
import org.example.service.ImageService;
import org.example.service.dto.FileDto;
import org.example.service.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {
    private final CandidateRepository candidateRepository;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public FileDto uploadImage(MultipartFile multipartFile, Long id) throws ImageStorageException {
        try {
            File image = createFile(multipartFile);
            Candidate candidate = candidateRepository.findById(id).get();
            if (candidate.getPhoto() != null) {
                Long fileId = candidate.getPhoto().getId();
                fileRepository.deleteById(fileId);
            }
            File saveImage = fileRepository.saveAndFlush(image);
            candidate.setPhoto(saveImage);
            candidateRepository.saveAndFlush(candidate);
            return fileMapper.fromEntityToResponseDto(saveImage);
        } catch (IOException e) {
            throw new ImageStorageException(e.getMessage());
        }
    }

    @Override
    public byte[] getImage(Long id) {
        Candidate candidate = candidateRepository.findById(id).get();
        if (candidate.getPhoto() != null) {
            return candidate.getPhoto().getContent();
        } else {
            throw new ImageNotFoundException(String.format("Image of user with id - %s not found", id));
        }
    }
    private File createFile(MultipartFile multipartFile) throws IOException {
        return File.builder()
                .name(multipartFile.getOriginalFilename())
                .content(multipartFile.getBytes()).build();
    }
}
