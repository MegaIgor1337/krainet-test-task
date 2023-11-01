package krainet.test.util;

import lombok.experimental.UtilityClass;
import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.entity.Direction;
import krainet.test.service.dto.CandidateRequestDto;
import krainet.test.service.dto.CandidateResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static krainet.test.util.FileTestData.getDocument;
import static krainet.test.util.FileTestData.getImage;

@UtilityClass
public class CandidateTestData {
    public static final String CANDIDATE_URL = "/api/v1/candidates";
    public static final Long ID = 1L;
    public static final Long INVALID_ID = 100L;
    public static final String CANDIDATE_URL_PUT = String.format("%s/%s", CANDIDATE_URL, ID);
    public static final String CANDIDATE_URL_INVALID_PUT = String.format("%s/%s", CANDIDATE_URL, INVALID_ID);

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




}
