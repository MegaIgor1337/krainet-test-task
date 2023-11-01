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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@Service
@RequiredArgsConstructor
public class CvServiceImpl implements CvService {
    private final CandidateRepository candidateRepository;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    @Override
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
            return fileMapper.fromEntityToResponseDto(saveCv);
        } catch (IOException e) {
            throw new CvStorageException(e.getMessage());
        }
    }

    @Override
    public byte[] getCv(Long id) {
        Candidate candidate = candidateRepository.findById(id).get();
        if (candidate.getCv() != null) {
            return candidate.getCv().getContent();
        } else {
            throw new CvNotFoundException(String.format("Cv of user with id - %s not found", id));
        }
    }


    private File createFile(MultipartFile multipartFile) throws IOException {
        return File.builder()
                .name(multipartFile.getOriginalFilename())
                .content(multipartFile.getBytes()).build();
    }

}
