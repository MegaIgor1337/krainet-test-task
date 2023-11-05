package krainet.test.service.impl;

import krainet.test.persistence.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import krainet.test.api.exceptions.ImageNotFoundException;
import krainet.test.api.exceptions.ImageStorageException;
import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.entity.File;
import krainet.test.persistence.repository.CandidateRepository;
import krainet.test.service.ImageService;
import krainet.test.service.dto.FileDto;
import krainet.test.service.mapper.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static krainet.test.service.util.FileUtil.createFile;


@Slf4j
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
            log.info("Upload image - {}", saveImage.getName());
            return fileMapper.fromEntityToResponseDto(saveImage);
        } catch (IOException e) {
            throw new ImageStorageException(e.getMessage());
        }
    }

    @Override
    public byte[] getImage(Long id) {
        log.info("Get image of the candidate with id - {}", id);
        Candidate candidate = candidateRepository.findById(id).get();
        if (candidate.getPhoto() != null) {
            return candidate.getPhoto().getContent();
        } else {
            throw new ImageNotFoundException(String.format("Image of the candidate with id - %s not found", id));
        }
    }
}
