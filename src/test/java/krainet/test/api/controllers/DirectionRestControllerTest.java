package krainet.test.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import krainet.test.api.controller.DirectionRestController;
import krainet.test.service.DirectionService;
import krainet.test.service.TestService;
import krainet.test.service.dto.DirectionRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static krainet.test.util.DirectionTestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DirectionRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DirectionRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TestService testService;
    @MockBean
    private DirectionService directionService;

    @Test
    void shouldBeStatusIsCreatedWhenCreateDirectionIsSuccessful() throws Exception {
        DirectionRequestDto request = createRequestDirection();

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

    @Test
    public void shouldReturnCreatedWhenUpdateIsSuccessful() throws Exception {
        DirectionRequestDto request = createRequestDirectionWithTest();

        when(directionService.isDirectionExist(DIRECTION_ID)).thenReturn(true);
        when(testService.isTestExist(request.testId())).thenReturn(true);

        mockMvc.perform(put(DIRECTION_URL_POST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        DirectionRequestDto request = createInvalidRequest();

        when(directionService.isDirectionExist(DIRECTION_ID)).thenReturn(true);

        mockMvc.perform(put(DIRECTION_URL_POST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenDirectionDtoIsInvalid() throws Exception {
        DirectionRequestDto request = createInvalidRequest();

        when(directionService.isDirectionExist(DIRECTION_ID)).thenReturn(true);

        mockMvc.perform(put(DIRECTION_URL_POST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenJsonDirectionDtoIsInvalid() throws Exception {
        String request = getInvalidJsonRequest();

        when(directionService.isDirectionExist(DIRECTION_ID)).thenReturn(true);

        mockMvc.perform(put(DIRECTION_URL_POST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithAllParams() throws Exception {

        mockMvc.perform(get(DIRECTION_URL_ALL_PARAMS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithPage() throws Exception {

        mockMvc.perform(get(DIRECTION_URL_GET_PAGE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithName() throws Exception {
        mockMvc.perform(get(DIRECTION_URL_GET_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void shouldReturnOkWhenGetIsSuccessfulWithoutParams() throws Exception {

        mockMvc.perform(get(DIRECTION_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}
