package krainet.test.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import krainet.test.api.controller.CandidateRestController;
import krainet.test.service.CandidateService;
import krainet.test.service.DirectionService;
import krainet.test.service.dto.CandidateRequestDto;
import krainet.test.util.CandidateTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static krainet.test.util.CandidateTestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        CandidateRequestDto request = createRequestDtoWithoutDirectionsId();

        mockMvc.perform(MockMvcRequestBuilders.post(CANDIDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidJsonRequest() throws Exception {
        String invalidRequestBody = getInvalidJson();

        mockMvc.perform(MockMvcRequestBuilders.post(CANDIDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCreatedWhenUpdateIsSuccessful() throws Exception {
        CandidateRequestDto request = createRequestDtoWithDirectionsId();

        when(candidateService.isCandidateExist(CandidateTestData.ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(CANDIDATE_URL_PUT)
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


    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithoutParams() throws Exception {

        mockMvc.perform(get(CANDIDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithPages() throws Exception {

        mockMvc.perform(get(CANDIDATE_URL_GET_PAGE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithSort() throws Exception {

        mockMvc.perform(get(CANDIDATE_URL_GET_SORTING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithFilter() throws Exception {

        mockMvc.perform(get(CANDIDATE_URL_FILTER_AND_PAGE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsAllParams() throws Exception {

        mockMvc.perform(get(CANDIDATE_URL_ALL_PARAMS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}
