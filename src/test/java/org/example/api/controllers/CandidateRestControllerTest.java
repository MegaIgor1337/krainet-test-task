package org.example.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.controller.CandidateRestController;
import org.example.service.CandidateService;
import org.example.service.DirectionService;
import org.example.service.TestService;
import org.example.service.dto.CandidateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.util.CandidateTestData.*;
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
        CandidateRequestDto request = createRequestDtoWithoutDirectionsId();

        mockMvc.perform(post(CANDIDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidJsonRequest() throws Exception {
        String invalidRequestBody = getInvalidJson();

        mockMvc.perform(post(CANDIDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCreatedWhenUpdateIsSuccessful() throws Exception {
        CandidateRequestDto request = createRequestDtoWithDirectionsId();

        when(candidateService.isCandidateExist(ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(true);

        mockMvc.perform(put(CANDIDATE_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }


    @Test
    public void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        CandidateRequestDto request = createInvalidRequestDto();

        when(candidateService.isCandidateExist(ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(true);

        mockMvc.perform(put(CANDIDATE_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNotFoundIfUrlInvalid() throws Exception {
        CandidateRequestDto request = createInvalidRequestDto();

        when(candidateService.isCandidateExist(ID)).thenReturn(true);

        mockMvc.perform(put(CANDIDATE_URL_INVALID_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

}
