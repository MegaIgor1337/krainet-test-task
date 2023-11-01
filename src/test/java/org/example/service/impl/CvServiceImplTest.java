package org.example.service.impl;

import org.example.api.exceptions.CvNotFoundException;
import org.example.persistence.entity.Candidate;
import org.example.persistence.entity.File;
import org.example.persistence.repository.CandidateRepository;
import org.example.persistence.repository.FileRepository;
import org.example.service.dto.FileDto;
import org.example.service.mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.example.util.CandidateTestData.*;
import static org.example.util.FileTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CvServiceImplTest {
    @InjectMocks
    private CvServiceImpl cvService;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private FileMapper fileMapper;

    @Test
    public void shouldCorrectSaveCvDocument() throws IOException {
        MultipartFile multipartFile = getRightMultipartPdfFile();
        Candidate candidate = createCandidateWithoutDirectionId();
        File saveCv = getDocument();
        FileDto fileDto = getFileDtoCv();

        when(candidateRepository.findById(CANDIDATE_ID)).thenReturn(Optional.of(candidate));
        when(fileRepository.saveAndFlush(any(File.class))).thenReturn(saveCv);
        when(fileMapper.fromEntityToResponseDto(saveCv)).thenReturn(fileDto);

        FileDto result = cvService.uploadCv(multipartFile, CANDIDATE_ID);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(multipartFile.getOriginalFilename(), result.name());
    }

    @Test
    public void shouldReturnCvByCandidateId() throws IOException {
        Candidate candidate = createCandidateWithCv();

        when(candidateRepository.findById(CANDIDATE_ID)).thenReturn(Optional.of(candidate));

        byte[] result = cvService.getCv(CANDIDATE_ID);

        assertNotNull(result);
        assertArrayEquals(getDocument().getContent(), result);
    }

    @Test
    public void shouldReturnExceptionWhenGetCvByCandidateId() throws IOException {
        Candidate candidate = createCandidateWithoutDirectionId();

        when(candidateRepository.findById(CANDIDATE_ID)).thenReturn(Optional.of(candidate));

        assertThrows(CvNotFoundException.class, () -> {
            cvService.getCv(CANDIDATE_ID);
        });
    }
}
