package krainet.test.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import krainet.test.api.controller.TestRestController;
import krainet.test.service.DirectionService;
import krainet.test.service.TestService;
import krainet.test.service.dto.TestRequestDto;
import krainet.test.util.TestTestData;
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
    public void shouldReturnBadRequestWhenInvalidRequest() throws Exception {
        String invalidRequestBody = TestTestData.getInvalidJsonRequest();

        mockMvc.perform(MockMvcRequestBuilders.post(TestTestData.TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestAddTestWithoutObligatoryParam() throws Exception {
        String requestBody = TestTestData.getJsonWithoutObligatoryParam();

        mockMvc.perform(MockMvcRequestBuilders.post(TestTestData.TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestAddTestWithNotExistedDirectionsId() throws Exception {
        TestRequestDto requestBody = TestTestData.createRequestTestDtoWithNotExistedDirectionsId();

        when(directionService.isDirectionExist(any(Long.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post(TestTestData.TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCreatedWhenUpdateIsSuccessfulWithoutDirections() throws Exception {
        TestRequestDto request = TestTestData.createRequestTestWithoutDirections();

        when(testService.isTestExist(TestTestData.TEST_ID)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(TestTestData.TEST_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnCreatedWhenUpdateIsSuccessfulWithDirections() throws Exception {
        TestRequestDto request = TestTestData.createRequestTestWithDirections();

        when(testService.isTestExist(TestTestData.TEST_ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(TestTestData.TEST_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNotFoundWhenUpdateIsSuccessfulWithNotExistsDirections() throws Exception {
        TestRequestDto request = TestTestData.createRequestTestWithDirections();

        when(testService.isTestExist(TestTestData.TEST_ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put(TestTestData.TEST_URL_PUT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNotFoundWhenUpdateIsSuccessfulWithNotExistsTestId() throws Exception {
        TestRequestDto request = TestTestData.createRequestTestWithDirections();

        when(testService.isTestExist(TestTestData.TEST_ID)).thenReturn(true);
        when(directionService.isDirectionExist(any(Long.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put(TestTestData.TEST_URL_PUT_INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithoutParams() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(TestTestData.TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithName() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get(TestTestData.TEST_URL_PAGE_TEST_FILTER_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithDirectionsId() throws Exception {

        when(directionService.isDirectionExist(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get(TestTestData.TEST_URL_PAGE_TEST_FILTER_ID_DIRECTIONS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithPageData() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(TestTestData.TEST_URL_PAGE_DATA)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithPageSize() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get(TestTestData.TEST_URL_PAGE_SIZE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}
