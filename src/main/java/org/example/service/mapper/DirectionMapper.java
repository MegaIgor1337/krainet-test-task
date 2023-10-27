package org.example.service.mapper;

import org.example.persistence.entity.Direction;
import org.example.persistence.entity.Test;
import org.example.persistence.repository.TestRepository;
import org.example.service.TestService;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class DirectionMapper {
    @Autowired
    protected TestService testService;

    public abstract DirectionResponseDto fromEntityToResponseDto(Direction direction);

    public Direction fromRequestDtoToEntity(DirectionRequestDto dto) {
        Direction direction = new Direction();
        direction.setName(dto.name());
        direction.setDescription(dto.description());

        if (dto.testId() != null) {
            direction.setTest(testService.findTestById(dto.testId()));
        } else {
            direction.setTest(null);
        }

        return direction;
    }
}
