package krainet.test.service.mapper;

import krainet.test.persistence.entity.Direction;
import krainet.test.persistence.repository.TestRepository;
import krainet.test.service.dto.DirectionRequestDto;
import krainet.test.service.dto.DirectionResponseDto;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class DirectionMapper {
    @Autowired
    protected TestRepository testRepository;

    public abstract DirectionResponseDto fromEntityToResponseDto(Direction direction);

    public Direction fromRequestDtoToEntity(DirectionRequestDto dto) {
        Direction direction = new Direction();
        direction.setName(dto.name());
        direction.setDescription(dto.description());

        if (dto.testId() != null) {
            direction.setTest(testRepository
                    .findById(dto.testId()).orElse(null));
        }

        return direction;
    }
}
