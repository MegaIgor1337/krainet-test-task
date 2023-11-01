package krainet.test.api.controllers;

import krainet.test.PostgreSQLTestContainerExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
