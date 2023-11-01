package krainet.test.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import krainet.test.util.CandidateTestData;
import krainet.test.api.controller.CandidateRestController;
import krainet.test.service.CandidateService;
import krainet.test.service.DirectionService;
import krainet.test.service.dto.CandidateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CandidateRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CandidateRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CandidateService candidateService;
    @MockBean
    private DirectionService directionService;

    @Test
    void shouldBeStatusIsCreatedWhenCreateCandidateWithoutIDirectionsIsSuccessful() throws Exception {
        CandidateRequestDto request = CandidateTestData.createRequestDtoWithoutDirectionsId();

        mockMvc.perform(MockMvcRequestBuilders.post(CandidateTestData.CANDIDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidJsonRequest() throws Exception {
        String invalidRequestBody = CandidateTestData.getInvalidJson();

        mockMvc.perform(MockMvcRequestBuilders.post(CandidateTestData.CANDIDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCreatedWhenUpdateIsSuccessful() throws Exception {
        CandidateRequestDto request = CandidateTestData.createRequestDtoWithDirectionsId();

        when(candidateService.isCandidateExist(CandidateTestData.ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(CandidateTestData.CANDIDATE_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }


    @Test
    public void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        CandidateRequestDto request = CandidateTestData.createInvalidRequestDto();

        when(candidateService.isCandidateExist(CandidateTestData.ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(CandidateTestData.CANDIDATE_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundIfUrlInvalid() throws Exception {
        CandidateRequestDto request = CandidateTestData.createInvalidRequestDto();

        when(candidateService.isCandidateExist(CandidateTestData.ID)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(CandidateTestData.CANDIDATE_URL_INVALID_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

}
