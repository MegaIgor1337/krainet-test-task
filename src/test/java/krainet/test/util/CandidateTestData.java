package krainet.test.util;

import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.entity.Direction;
import krainet.test.service.dto.*;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static krainet.test.util.FileTestData.getDocument;
import static krainet.test.util.FileTestData.getImage;

@UtilityClass
public class CandidateTestData {
    public static final String CANDIDATE_URL = "/api/v1/candidates";
    public static final Long ID = 1L;
    public static final Integer PAGE_NUMBER = 1;
    public static final Integer PAGE_SIZE = 3;
    public static final Long INVALID_ID = 100L;
    public static final String CANDIDATE_NAME = "Michael";
    public static final String SORTED_CANDIDATE_NAME = "David";
    public static final String CANDIDATE_URL_PUT = String.format("%s/%s", CANDIDATE_URL, ID);
    public static final String CANDIDATE_URL_INVALID_PUT = String.format("%s/%s", CANDIDATE_URL, INVALID_ID);
    public static final String CANDIDATE_LAST_NAME = "Johnson";
    public static final String CANDIDATE_URL_GET_PAGE = String
            .format("%s?pageNumber=%s&pageSize=%s", CANDIDATE_URL, PAGE_NUMBER, PAGE_SIZE);
    public static final String CANDIDATE_URL_GET_NAME = String
            .format("%s?firstName=%s", CANDIDATE_URL, CANDIDATE_NAME);
    public static final String CANDIDATE_URL_FILTER_AND_PAGE = String
            .format("%s?firstName=%s&lastName=%s&pageNumber=%s&pageSize=%s",
                    CANDIDATE_URL, CANDIDATE_NAME, CANDIDATE_LAST_NAME, "0", PAGE_SIZE);
    public static final String CANDIDATE_URL_ALL_PARAMS = String
            .format("%s?sort=FIRST_NAME&order=ASC&firstName=%s&lastName=%s&pageNumber=%s&pageSize=%s",
                    CANDIDATE_URL, CANDIDATE_NAME, CANDIDATE_LAST_NAME, "0", PAGE_SIZE);
    public static final String CANDIDATE_URL_GET_SORTING = String
            .format("%s?sort=FIRST_NAME&order=ASC",
                    CANDIDATE_URL);

    public static Candidate createCandidateWithoutDirectionId() {
        return Candidate.builder()
                .firstName("Igor")
                .lastName("Yakubovich")
                .fatherName("Sergeevich")
                .description("Very good programmer")
                .id(1L)
                .build();
    }


    public static Candidate createCandidateWithImage() throws IOException {
        return Candidate.builder()
                .firstName("Igor")
                .lastName("Yakubovich")
                .fatherName("Sergeevich")
                .photo(getImage())
                .description("Very good programmer")
                .id(1L)
                .build();
    }

    public static Candidate createCandidateWithCv() throws IOException {
        return Candidate.builder()
                .firstName("Igor")
                .lastName("Yakubovich")
                .fatherName("Sergeevich")
                .cv(getDocument())
                .description("Very good programmer")
                .id(1L)
                .build();
    }

    public static Candidate createCandidateWithDirections() {
        return Candidate.builder()
                .firstName("Igor")
                .lastName("Yakubovich")
                .fatherName("Sergeevich")
                .description("Very good programmer")
                .directions(List.of(
                        Direction.builder()
                                .id(1L)
                                .name("Test Direction")
                                .description("Nice Direction")
                                .build()
                ))
                .build();
    }

    public static CandidateRequestDto createRequestDtoWithDirectionsId() {
        return CandidateRequestDto.builder()
                .firstName("First name")
                .lastName("Last name")
                .fatherName("Father name")
                .description("Description")
                .directionsId(Set.of(
                                1L,
                                2L
                        )
                ).build();
    }


    public static CandidateRequestDto createRequestDtoWithInvalidDirectionsId() {
        return CandidateRequestDto.builder()
                .firstName("First name")
                .lastName("Last name")
                .fatherName("Father name")
                .description("Description")
                .directionsId(Set.of(
                                100L,
                                242L
                        )
                ).build();
    }

    public static CandidateRequestDto createInvalidRequestDto() {
        return CandidateRequestDto.builder()
                .lastName("Last name")
                .fatherName("Father name")
                .description("Description")
                .directionsId(Set.of(
                                3L,
                                1L
                        )
                ).build();
    }

    public static CandidateResponseDto createResponseDtoWithDirections() {
        return CandidateResponseDto.builder()
                .id(1L)
                .firstName("First name")
                .lastName("Last name")
                .fatherName("Father name")
                .description("Description")
                .directionsId(List.of(1L, 2L))
                .build();
    }

    public static CandidateResponseDto createResponseDtoWithoutDirections() {
        return CandidateResponseDto.builder()
                .id(1L)
                .firstName("First name")
                .lastName("Last name")
                .fatherName("Father name")
                .description("Description")
                .build();
    }

    public static CandidateRequestDto createRequestDtoWithoutDirectionsId() {
        return CandidateRequestDto.builder()
                .firstName("First name")
                .lastName("Last name")
                .fatherName("Father name")
                .description("Description")
                .build();
    }

    public static String getInvalidJson() {
        return "{\n" +
               "  \"firstName\": \"Candidate first Name\",\n" +
               "  \"lastName\": \"Candidate last Name\",\n" +
               "  \"fatherName\": \"Candidate father Name\",\n" +
               "  \"description\": " +
               "  \"directionsId\": [\n" +
               "    1,\n" +
               "    2\n" +
               "  ]\n" +
               "}";
    }

    public static List<Candidate> createListOfCandidates() {
        return List.of(
                Candidate.builder()
                        .id(1L)
                        .firstName("First Name Candidate 1")
                        .lastName("Last Name Candidate 1")
                        .fatherName("Father Name Candidate 1")
                        .description("Description 1")
                        .directions(List.of(
                                Direction.builder()
                                        .id(1L)
                                        .name("Direction")
                                        .description("Description")
                                        .build()
                        ))
                        .build(),
                Candidate.builder()
                        .id(2L)
                        .firstName("First Name Candidate 2")
                        .lastName("Last Name Candidate 2")
                        .fatherName("Father Name Candidate 2")
                        .description("Description 2")
                        .directions(List.of(
                                Direction.builder()
                                        .id(2L)
                                        .name("Direction")
                                        .description("Description")
                                        .build()
                        ))
                        .build()
        );
    }


    public static List<Candidate> createListOfCandidatesAfterFilter() {
        return List.of(
                Candidate.builder()
                        .id(1L)
                        .firstName("First Name Candidate 1")
                        .lastName("Last Name Candidate 1")
                        .fatherName("Father Name Candidate 1")
                        .description("Description 1")
                        .directions(List.of(
                                Direction.builder()
                                        .id(1L)
                                        .name("Direction")
                                        .description("Description")
                                        .build()
                        ))
                        .build()
        );
    }

    public static List<CandidateResponseDto> createListOfCandidateResponseDto() {
        return List.of(
                CandidateResponseDto.builder()
                        .id(1L)
                        .firstName("First Name Candidate 1")
                        .lastName("Last Name Candidate 1")
                        .fatherName("Father Name Candidate 1")
                        .description("Description 1")
                        .directionsId(List.of(1L))
                        .build(),
                CandidateResponseDto.builder()
                        .id(2L)
                        .firstName("First Name Candidate 2")
                        .lastName("Last Name Candidate 2")
                        .fatherName("Father Name Candidate 2")
                        .description("Description 2")
                        .directionsId(List.of(2L))
                        .build()
        );
    }

    public static List<CandidateResponseDto> createListOfCandidateResponseDtoAfterFilter() {
        return List.of(
                CandidateResponseDto.builder()
                        .id(1L)
                        .firstName("First Name Candidate 1")
                        .lastName("Last Name Candidate 1")
                        .fatherName("Father Name Candidate 1")
                        .description("Description 1")
                        .directionsId(List.of(1L))
                        .build()
        );
    }

    public static PageRequestDto createPageRequestDto() {
        return PageRequestDto.builder()
                .pageNumber(0)
                .pageSize(2)
                .build();
    }

    public static List<SortCandidateFields> createListOfSortCandidateFields() {
        return List.of(
                SortCandidateFields.FIRST_NAME,
                SortCandidateFields.DIRECTIONS_ID
        );
    }

    public static CandidateRequestFilter createCandidateFilter() {
        return CandidateRequestFilter.builder()
                .firstName("First Name Candidate 1")
                .build();
    }
}
