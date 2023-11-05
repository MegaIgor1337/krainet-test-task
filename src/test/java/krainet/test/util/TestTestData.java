package krainet.test.util;

import krainet.test.persistence.entity.Direction;
import krainet.test.persistence.entity.Test;
import krainet.test.service.dto.TestRequestDto;
import krainet.test.service.dto.TestRequestFilter;
import krainet.test.service.dto.TestResponseDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Set;

@UtilityClass
public class TestTestData {
    public static final String TEST_URL = "/api/v1/tests";
    public static final Long TEST_ID = 1L;
    public static final Long INVALID_ID = 100L;
    public static final String TEST_URL_PUT = String.format("%s/%s", TEST_URL, TEST_ID);
    public static final String TEST_URL_PUT_INVALID_ID = String.format("%s/%s", TEST_URL, INVALID_ID);
    public static final String TEST_URL_PAGE_DATA = String.format("%s?pageNumber=0&pageSize=2", TEST_URL);
    public static final String TEST_URL_PAGE_SIZE = String.format("%s?pageSize=3", TEST_URL);
    public static final String TEST_URL_PAGE_TEST_FILTER_NAME = String.format("%s?testName=Test 1", TEST_URL);
    public static final String TEST_URL_PAGE_TEST_FILTER_ID_DIRECTIONS = String
            .format("%s?directionsId=1", TEST_URL);
    public static final String TEST_URL_PAGE_TEST_FILTER_ALL_PARAMS = String
            .format("%s?testName=Test 1&directionsId=2", TEST_URL);


    public static Test createTestWithoutDirections() {
        return Test.builder()
                .id(1L)
                .name("Test")
                .description("Test")
                .build();
    }

    public static Test createTestWithDirections() {
        return Test.builder()
                .id(1L)
                .name("Test1")
                .description("Description1")
                .directions(
                        List.of(Direction.builder()
                                        .id(1L)
                                        .name("backend")
                                        .description("Good")
                                        .build(),
                                Direction.builder()
                                        .id(2L)
                                        .name("backend")
                                        .description("Nice")
                                        .build()
                        )
                ).build();
    }

    public static TestRequestDto createRequestTestWithoutDirections() {
        return TestRequestDto.builder()
                .name("Test1")
                .description("Description1")
                .build();
    }

    public static TestRequestDto createRequestTestWithDirections() {
        return TestRequestDto.builder()
                .name("Test1")
                .description("Description1")
                .directionsId(
                        Set.of(
                                1L,
                                2L
                        )
                ).build();
    }

    public static TestRequestDto createInvalidRequest() {
        return TestRequestDto.builder()
                .description("Description1")
                .directionsId(
                        Set.of(
                                1L,
                                2L
                        )
                ).build();
    }

    public static String getInvalidJsonRequest() {
        return "{\"invalid_field\":123}";
    }

    public static String getJsonWithoutObligatoryParam() {
        return "{\"name\":\"\"}";
    }

    public static TestRequestDto createRequestTestDtoWithNotExistedDirectionsId() {
        return TestRequestDto.builder()
                .name("Test1")
                .description("Description1")
                .directionsId(
                        Set.of(
                                10L,
                                20L
                        )
                ).build();
    }

    public static Test createTestWithoutId() {
        return Test.builder()
                .name("Test1")
                .description("Description1")
                .build();
    }

    public static Test createAddedTestWithId() {
        return Test.builder()
                .id(1L)
                .name("Test1")
                .description("Description1")
                .build();
    }

    public static TestResponseDto createResponseTestDtoWithoutDirections() {
        return TestResponseDto.builder()
                .name("Test1")
                .description("Description1")
                .build();
    }

    public static Test createTestWithoutIdWithDirections() {
        return Test.builder()
                .name("Test1")
                .description("Description1")
                .directions(
                        List.of(Direction.builder()
                                        .id(1L)
                                        .name("backend")
                                        .description("Good")
                                        .build(),
                                Direction.builder()
                                        .id(2L)
                                        .name("backend")
                                        .description("Nice")
                                        .build()
                        )
                ).build();
    }

    public TestResponseDto createResponseTestDtoWithDirections() {
        return TestResponseDto.builder()
                .name("Test1")
                .description("Description1")
                .directionsId(
                        List.of(
                                1L,
                                2L
                        )
                ).build();
    }

    public static TestRequestDto createRequestTestWithInvalidDirections() {
        return TestRequestDto.builder()
                .name("Test1")
                .description("Description1")
                .directionsId(
                        Set.of(
                                100L,
                                200L
                        )
                ).build();
    }

    public static Page<Test> createPageOfTests() {
        return new PageImpl<>(List.of(Test.builder()
                        .id(1L)
                        .name("Test 1")
                        .description("Description 1")
                        .build(),
                Test.builder()
                        .name("Test 2")
                        .description("Description 2")
                        .build()));
    }

    public static TestRequestFilter createEmptyTestFilter() {
        return TestRequestFilter.builder().build();
    }

    public static TestRequestFilter createNotEmptyTestFilter() {
        return TestRequestFilter.builder()
                .testName("Test 1")
                .directionsId(List.of(1L)).build();
    }

    public static List<TestResponseDto> createListOfTestsResponseDto() {
        return List.of(
                TestResponseDto.builder()
                        .name("Test 1")
                        .description("Description 1")
                        .build(),
                TestResponseDto.builder()
                        .name("Test 2")
                        .description("Description 2")
                        .build()
        );
    }

    public static List<Test> createListOfTestsWithDirection() {
        return List.of(Test.builder()
                        .id(1L)
                        .name("Test 1")
                        .description("Description 1")
                        .directions(
                                List.of(Direction.builder()
                                        .name("Direction 1")
                                        .description("Description 1")
                                        .build())
                        )
                        .build(),
                Test.builder()
                        .name("Test 2")
                        .description("Description 2")
                        .directions(
                                List.of(Direction.builder()
                                        .name("Direction 1")
                                        .description("Description 1")
                                        .build())
                        )
                        .build());
    }

    public static List<TestResponseDto> createListOfTestsResponseDtoWithDirections() {
        return List.of(
                TestResponseDto.builder()
                        .name("Test 1")
                        .description("Description 1")
                        .directionsId(List.of(
                                        1L
                                )
                        )
                        .build(),
                TestResponseDto.builder()
                        .name("Test 2")
                        .description("Description 2")
                        .build()
        );
    }
}
