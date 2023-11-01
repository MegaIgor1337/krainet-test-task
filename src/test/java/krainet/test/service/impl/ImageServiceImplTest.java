package krainet.test.service.impl;

import krainet.test.persistence.entity.File;
import krainet.test.persistence.repository.FileRepository;
import krainet.test.service.dto.FileDto;
import krainet.test.service.mapper.FileMapper;
import krainet.test.util.CandidateTestData;
import krainet.test.util.FileTestData;
import krainet.test.api.exceptions.ImageNotFoundException;
import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.repository.CandidateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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
        MultipartFile multipartFile = FileTestData.getRightMultipartImage();
        Candidate candidate = CandidateTestData.createCandidateWithoutDirectionId();
        File saveImage = FileTestData.getImage();
        FileDto fileDto = FileTestData.getFileDtoImage();

        when(candidateRepository.findById(FileTestData.CANDIDATE_ID)).thenReturn(Optional.of(candidate));
        when(fileRepository.saveAndFlush(any(File.class))).thenReturn(saveImage);
        when(fileMapper.fromEntityToResponseDto(saveImage)).thenReturn(fileDto);

        FileDto result = imageService.uploadImage(multipartFile, FileTestData.CANDIDATE_ID);

        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(multipartFile.getOriginalFilename(), result.name());
    }

    @Test
    public void shouldReturnImageByCandidateId() throws IOException {
        Candidate candidate = CandidateTestData.createCandidateWithImage();

        when(candidateRepository.findById(FileTestData.CANDIDATE_ID)).thenReturn(Optional.of(candidate));

        byte[] result = imageService.getImage(FileTestData.CANDIDATE_ID);

        assertNotNull(result);
        Assertions.assertArrayEquals(FileTestData.getImage().getContent(), result);
    }

    @Test
    public void shouldReturnExceptionWhenGetImageByCandidateId() throws IOException {
        Candidate candidate = CandidateTestData.createCandidateWithoutDirectionId();

        when(candidateRepository.findById(FileTestData.CANDIDATE_ID)).thenReturn(Optional.of(candidate));

        assertThrows(ImageNotFoundException.class, () -> {
            imageService.getImage(FileTestData.CANDIDATE_ID);
        });
    }
}
