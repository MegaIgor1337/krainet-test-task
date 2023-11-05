package krainet.test.service.impl;

import krainet.test.api.exceptions.CvStorageException;
import krainet.test.persistence.entity.File;
import krainet.test.persistence.repository.CandidateRepository;
import krainet.test.persistence.repository.FileRepository;
import krainet.test.service.dto.FileDto;
import krainet.test.service.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import krainet.test.api.exceptions.CvNotFoundException;
import krainet.test.persistence.entity.Candidate;
import krainet.test.service.CvService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static krainet.test.service.util.FileUtil.createFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CvServiceImpl implements CvService {
    private final CandidateRepository candidateRepository;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public FileDto uploadCv(MultipartFile multipartFile, Long id) throws CvStorageException {
        try {
            File cv = createFile(multipartFile);
            Candidate candidate = candidateRepository.findById(id).get();
            if (candidate.getCv() != null) {
                Long cvId = candidate.getCv().getId();
                candidateRepository.deleteById(cvId);
            }
            File saveCv = fileRepository.saveAndFlush(cv);
            candidate.setCv(saveCv);
            candidateRepository.saveAndFlush(candidate);
            log.info("Upload cv - {}", saveCv.getName());
            return fileMapper.fromEntityToResponseDto(saveCv);
        } catch (IOException e) {
            throw new CvStorageException(e.getMessage());
        }
    }

    @Override
    public byte[] getCv(Long id) {
        log.info("Get cv of the candidate with id - {}", id);
        Candidate candidate = candidateRepository.findById(id).get();
        if (candidate.getCv() != null) {
            return candidate.getCv().getContent();
        } else {
            throw new CvNotFoundException(String.format("Cv of user with id - %s not found", id));
        }
    }
}