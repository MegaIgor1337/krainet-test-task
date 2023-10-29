package org.example.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.controllers.controller.TestRestController;
import org.example.service.DirectionService;
import org.example.service.TestService;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestRequestFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.example.util.TestTestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DirectionService directionService;
    @MockBean
    private TestService testService;

    @Test
    void shouldBeStatusIsCreatedWhenCreateTestIsSuccessful() throws Exception {
        TestRequestDto request = createRequestTestWithoutDirections();

        mockMvc.perform(post(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidRequest() throws Exception {
        String invalidRequestBody = getInvalidJsonRequest();

        mockMvc.perform(post(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestAddTestWithoutObligatoryParam() throws Exception {
        String requestBody = getJsonWithoutObligatoryParam();

        mockMvc.perform(post(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestAddTestWithNotExistedDirectionsId() throws Exception {
        TestRequestDto requestBody = createRequestTestDtoWithNotExistedDirectionsId();

        when(directionService.isDirectionExist(any(Long.class))).thenReturn(false);

        mockMvc.perform(post(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCreatedWhenUpdateIsSuccessfulWithoutDirections() throws Exception {
        TestRequestDto request = createRequestTestWithoutDirections();

        when(testService.isTestExist(TEST_ID)).thenReturn(true);

        mockMvc.perform(put(TEST_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnCreatedWhenUpdateIsSuccessfulWithDirections() throws Exception {
        TestRequestDto request = createRequestTestWithDirections();

        when(testService.isTestExist(TEST_ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(true);

        mockMvc.perform(put(TEST_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNotFoundWhenUpdateIsSuccessfulWithNotExistsDirections() throws Exception {
        TestRequestDto request = createRequestTestWithDirections();

        when(testService.isTestExist(TEST_ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(false);

        mockMvc.perform(put(TEST_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNotFoundWhenUpdateIsSuccessfulWithNotExistsTestId() throws Exception {
        TestRequestDto request = createRequestTestWithDirections();

        when(testService.isTestExist(TEST_ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(false);

        mockMvc.perform(put(TEST_URL_PUT_INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithoutParams() throws Exception {
        when(testService.getTests(null, 0, null))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithName() throws Exception {
        when(testService.getTests(new TestRequestFilter("Test 1", null),
                0, null))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(TEST_URL_PAGE_TEST_FILTER_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithDirectionsId() throws Exception {
        when(testService.getTests(new TestRequestFilter(null, List.of(1L)),
                0, null))
                .thenReturn(new ArrayList<>());
        when(directionService.isDirectionExist(1L)).thenReturn(true);

        mockMvc.perform(get(TEST_URL_PAGE_TEST_FILTER_ID_DIRECTIONS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithPageData() throws Exception {
        when(testService.getTests(new TestRequestFilter(null, null),
                0, 2))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(TEST_URL_PAGE_DATA)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithPageSize() throws Exception {
        when(testService.getTests(new TestRequestFilter(null, null),
                0, 3))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(TEST_URL_PAGE_SIZE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}
