package org.example.service.impl;

import org.example.api.exceptions.ImageNotFoundException;
import org.example.persistence.entity.Candidate;
import org.example.persistence.entity.File;
import org.example.persistence.repository.CandidateRepository;
import org.example.persistence.repository.FileRepository;
import org.example.service.dto.FileDto;
import org.example.service.mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.example.util.CandidateTestData.createCandidateWithImage;
import static org.example.util.CandidateTestData.createCandidateWithoutDirectionId;
import static org.example.util.FileTestData.*;
import static org.example.util.FileTestData.CANDIDATE_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {
    @InjectMocks
    private ImageServiceImpl imageService;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private FileMapper fileMapper;

    @Test
    public void shouldCorrectSaveImage() throws IOException {
        MultipartFile multipartFile = getRightMultipartImage();
        Candidate candidate = createCandidateWithoutDirectionId();
        File saveImage = getImage();
        FileDto fileDto = getFileDtoImage();

        when(candidateRepository.findById(CANDIDATE_ID)).thenReturn(Optional.of(candidate));
        when(fileRepository.saveAndFlush(any(File.class))).thenReturn(saveImage);
        when(fileMapper.fromEntityToResponseDto(saveImage)).thenReturn(fileDto);

        FileDto result = imageService.uploadImage(multipartFile, CANDIDATE_ID);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(multipartFile.getOriginalFilename(), result.name());
    }

    @Test
    public void shouldReturnImageByCandidateId() throws IOException {
        Candidate candidate = createCandidateWithImage();

        when(candidateRepository.findById(CANDIDATE_ID)).thenReturn(Optional.of(candidate));

        byte[] result = imageService.getImage(CANDIDATE_ID);

        assertNotNull(result);
        assertArrayEquals(getImage().getContent(), result);
    }

    @Test
    public void shouldReturnExceptionWhenGetImageByCandidateId() throws IOException {
        Candidate candidate = createCandidateWithoutDirectionId();

        when(candidateRepository.findById(CANDIDATE_ID)).thenReturn(Optional.of(candidate));

        assertThrows(ImageNotFoundException.class, () -> {
            imageService.getImage(CANDIDATE_ID);
        });
    }
}
