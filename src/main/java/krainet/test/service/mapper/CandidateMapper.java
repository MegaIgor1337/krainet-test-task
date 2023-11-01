package krainet.test.service.mapper;

import krainet.test.persistence.entity.Candidate;
import krainet.test.persistence.entity.Direction;
import krainet.test.persistence.repository.DirectionRepository;
import krainet.test.service.dto.CandidateRequestDto;
import krainet.test.service.dto.CandidateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class CandidateMapper {
    private final DirectionMapper directionMapper;
    private final DirectionRepository directionRepository;


    public CandidateResponseDto fromEntityToResponseDto(Candidate candidate) {
        List<Long> directionsDto;

        if (candidate.getDirections() == null) {
            directionsDto = null;
        } else {
            directionsDto = candidate.getDirections().stream()
                    .map(Direction::getId).toList();
        }

        CandidateResponseDto candidateResponseDto = CandidateResponseDto.builder()
                .id(candidate.getId())
                .firstName(candidate.getFirstName())
                .lastName(candidate.getLastName())
                .fatherName(candidate.getFatherName())
                .description(candidate.getDescription())
                .directionsId(directionsDto)
                .build();

        return candidateResponseDto;

    }

    public Candidate fromRequestDtoToEntity(CandidateRequestDto dto) {
        Candidate candidate = Candidate
                .builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .fatherName(dto.fatherName())
                .description(dto.description())
                .build();


        if (dto.directionsId() == null) {
            return candidate;
        }

        List<Direction> directions = dto.directionsId().stream()
                .map(directionRepository::findById)
                .map(d -> d.orElse(null)).toList();

        candidate.setDirections(directions);

        return candidate;
    }

}
