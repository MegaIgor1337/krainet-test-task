package org.example.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.controllers.controller.DirectionRestController;
import org.example.service.DirectionService;
import org.example.service.TestService;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.util.DirectionTestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DirectionRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DirectionRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DirectionService directionService;

    @MockBean
    private TestService testService;

    @Test
    void shouldBeStatusIsCreatedWhenCreateDirectionIsSuccessful() throws Exception {
        DirectionRequestDto request = createRequestDirection();
        DirectionResponseDto response = createResponseDirection();

        when(directionService.addDirection(request)).thenReturn(response);
        when(testService.isTestExist(null)).thenReturn(true);

        mockMvc.perform(post(DIRECTION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidRequest() throws Exception {
        String invalidRequestBody = getInvalidJsonRequest();

        mockMvc.perform(post(DIRECTION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestAddDirectionWithoutObligatoryParam() throws Exception {
        String requestBody = getJsonWithoutObligatoryParam();

        mockMvc.perform(post(DIRECTION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
