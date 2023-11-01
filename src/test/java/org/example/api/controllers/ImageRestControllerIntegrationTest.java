package org.example.api.controllers;

import org.example.PostgreSQLTestContainerExtension;
import org.example.service.dto.CandidateRequestDto;
import org.example.service.dto.CandidateResponseDto;
import org.example.service.dto.FileDto;
import org.example.util.FileTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.io.IOException;

import static org.example.util.CandidateTestData.CANDIDATE_URL;
import static org.example.util.FileTestData.IMAGE_URL_UPLOAD;
import static org.example.util.FileTestData.getRightMultipartImage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(scripts = "classpath:testdata/clear_candidates_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/clear_file_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_image_test_data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:testdata/add_candidate_with_image_test_data.sql", executionPhase = BEFORE_TEST_METHOD)
})
@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageRestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

 /*   @Test
    public void shouldReturnFileDtoWhenUploadSuccess() throws IOException {

        HttpEntity<MockMultipartFile> requestEntity = new HttpEntity<>(getRightMultipartImage());

        ResponseEntity<FileDto> response = restTemplate.exchange(
                IMAGE_URL_UPLOAD,
                POST,
                requestEntity,
                FileDto.class
        );

        FileDto addedImage = response.getBody();

        assertNotNull(addedImage);
        assertEquals(requestEntity.getBody().getOriginalFilename(), addedImage.name());
    }*/

}
