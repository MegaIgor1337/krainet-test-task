package krainet.test.service.impl;

import krainet.test.persistence.entity.File;
import krainet.test.persistence.repository.FileRepository;
import krainet.test.service.dto.FileDto;
import krainet.test.service.mapper.FileMapper;
import krainet.test.util.CandidateTestData;
import krainet.test.api.exceptions.CvNotFoundException;
import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static krainet.test.util.FileTestData.*;
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
        Candidate candidate = CandidateTestData.createCandidateWithoutDirectionId();
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
        Candidate candidate = CandidateTestData.createCandidateWithCv();

        when(candidateRepository.findById(CANDIDATE_ID)).thenReturn(Optional.of(candidate));

        byte[] result = cvService.getCv(CANDIDATE_ID);

        assertNotNull(result);
        assertArrayEquals(getDocument().getContent(), result);
    }

    @Test
    public void shouldReturnExceptionWhenGetCvByCandidateId() throws IOException {
        Candidate candidate = CandidateTestData.createCandidateWithoutDirectionId();

        when(candidateRepository.findById(CANDIDATE_ID)).thenReturn(Optional.of(candidate));

        assertThrows(CvNotFoundException.class, () -> {
            cvService.getCv(CANDIDATE_ID);
        });
    }
}
