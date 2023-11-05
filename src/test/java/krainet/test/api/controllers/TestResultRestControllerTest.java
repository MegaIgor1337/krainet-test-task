package krainet.test.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import krainet.test.api.controller.TestResultRestController;
import krainet.test.service.CandidateService;
import krainet.test.service.TestResultService;
import krainet.test.service.TestService;
import krainet.test.service.dto.TestResultRequestDto;
import krainet.test.util.CandidateTestData;
import krainet.test.util.TestResultTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static krainet.test.util.TestResultTestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestResultRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestResultRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CandidateService candidateService;
    @MockBean
    private TestService testService;
    @MockBean
    private TestResultService testResultService;

    @Test
    void shouldBeStatusIsCreatedWhenCreateTestResultIsSuccessful() throws Exception {
        TestResultRequestDto request = TestResultTestData.createTestResultRequestDto();

        when(testService.isTestExist(request.testId())).thenReturn(true);
        when(candidateService.isCandidateExist(request.candidateId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post(TEST_RESULT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidJsonRequest() throws Exception {
        String invalidRequestBody = TestResultTestData.getInvalidJson();

        mockMvc.perform(MockMvcRequestBuilders.post(TEST_RESULT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCreatedWhenUpdateIsSuccessful() throws Exception {
        TestResultRequestDto request = TestResultTestData.createTestResultRequestDto();

        when(candidateService.isCandidateExist(request.candidateId())).thenReturn(true);
        when(testService.isTestExist(request.testId())).thenReturn(true);
        when((testResultService.isTestResultExist(TEST_RESULT_ID))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(TEST_RESULT_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }


    @Test
    public void shouldReturnNotFoundWhenRequestIsInvalid() throws Exception {
        TestResultRequestDto request = TestResultTestData.createInvalidRequestDtoNotFoundTestId();

        when(candidateService.isCandidateExist(request.candidateId())).thenReturn(true);
        when(testService.isTestExist(request.testId())).thenReturn(false);
        when(testResultService.isTestResultExist(TEST_RESULT_ID)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(TEST_RESULT_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNotFoundIfUrlInvalid() throws Exception {
        TestResultRequestDto request = TestResultTestData.createInvalidRequestDtoNotFoundTestId();

        when(candidateService.isCandidateExist(CandidateTestData.ID)).thenReturn(false);
        when(testService.isTestExist(request.testId())).thenReturn(true);
        when(testResultService.isTestResultExist(TEST_RESULT_ID)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(TEST_RESULT_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithoutParams() throws Exception {

        mockMvc.perform(get(TEST_RESULT_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithPages() throws Exception {

        mockMvc.perform(get(TEST_RESULT_URL_GET_PAGE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithSort() throws Exception {

        mockMvc.perform(get(TEST_RESULT_URL_GET_SORTING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithFilter() throws Exception {

        when(testService.isTestExist(TEST_ID)).thenReturn(true);
        when(candidateService.isCandidateExist(CANDIDATE_ID)).thenReturn(true);

        mockMvc.perform(get(TEST_RESULT_URL_FILTER_AND_PAGE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsAllParams() throws Exception {

        when(testService.isTestExist(TEST_ID)).thenReturn(true);
        when(candidateService.isCandidateExist(CANDIDATE_ID)).thenReturn(true);

        mockMvc.perform(get(TEST_RESULT_URL_ALL_PARAMS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}
