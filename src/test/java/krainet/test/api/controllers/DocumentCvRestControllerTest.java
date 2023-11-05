package krainet.test.api.controllers;

import krainet.test.api.controller.DocumentCvRestController;
import krainet.test.service.CvService;
import krainet.test.service.impl.CandidateServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static krainet.test.util.FileTestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DocumentCvRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DocumentCvRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CandidateServiceImpl candidateService;
    @MockBean
    private CvService cvService;

    @Test
    public void shouldBeStatusIsCreatedWhenCreateImageIsSuccessful() throws Exception {
        MockMultipartFile request = getRightMultipartPdfFile();

        when(candidateService.isCandidateExist(CANDIDATE_ID)).thenReturn(true);

        mockMvc.perform(multipart(HttpMethod.PUT, CV_URL_UPLOAD)
                        .file(request))
                .andExpect(status().isCreated());
    }


    @Test
    public void shouldBeStatusBadRequestWhenImageInvalid() throws Exception {
        MockMultipartFile request = getBadMultipartPdfFile();

        when(candidateService.isCandidateExist(CANDIDATE_ID)).thenReturn(true);

        mockMvc.perform(multipart(HttpMethod.PUT, CV_URL_UPLOAD)
                        .file(request))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldBeStatusNotFoundWhenCandidateIsNotExist() throws Exception {
        MockMultipartFile request = getRightMultipartImage();

        when(candidateService.isCandidateExist(INVALID_CANDIDATE_ID)).thenReturn(false);

        mockMvc.perform(multipart(HttpMethod.PUT, СМ_URL_UPLOAD_INVALID)
                        .file(request))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldBeStatusIsOkWhenDownloadImage() throws Exception {
        byte[] imageByte = getDocument().getContent();

        when(candidateService.isCandidateExist(CANDIDATE_ID)).thenReturn(true);
        when(cvService.getCv(CANDIDATE_ID)).thenReturn(imageByte);

        mockMvc.perform(get(CV_URL_UPLOAD))
                .andExpect(status().isOk());

    }

}
