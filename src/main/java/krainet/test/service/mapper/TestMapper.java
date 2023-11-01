package krainet.test.service.mapper;

import krainet.test.service.dto.TestRequestDto;
import lombok.RequiredArgsConstructor;
import krainet.test.persistence.entity.Direction;
import krainet.test.persistence.entity.Test;
import krainet.test.persistence.repository.DirectionRepository;
import krainet.test.service.dto.TestResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestMapper {

    protected final DirectionRepository directionRepository;

    public  TestResponseDto fromEntityToResponseDto(Test test) {
        List<Long> directionsId = null;

        if (test.getDirections() != null) {
            directionsId = test.getDirections().stream()
                    .map(Direction::getId).toList();
        }

        return TestResponseDto.builder()
                .id(test.getId())
                .name(test.getName())
                .description(test.getDescription())
                .directionsId(directionsId).build();

    }

    public Test fromRequestDtoToEntity(TestRequestDto dto) {
        Test test = Test.builder()
                .name(dto.name())
                .description(dto.description())
                .build();

        if (dto.directionsId() == null) {
            test.setDirections(null);
            return test;
        }

        List<Direction> directions = dto.directionsId().stream()
                .map(directionRepository::findById)
                .map(d -> d.orElse(null)).toList();

        test.setDirections(directions);

        return test;
    }
}
