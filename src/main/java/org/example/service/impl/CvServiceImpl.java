package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.exceptions.CvNotFoundException;
import org.example.api.exceptions.CvStorageException;
import org.example.persistence.entity.Candidate;
import org.example.persistence.entity.File;
import org.example.persistence.repository.CandidateRepository;
import org.example.persistence.repository.FileRepository;
import org.example.service.CvService;
import org.example.service.dto.FileDto;
import org.example.service.mapper.FileMapper;
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
