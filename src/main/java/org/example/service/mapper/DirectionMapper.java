package org.example.service.mapper;

import org.example.persistence.entity.Direction;
import org.example.service.dto.DirectionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DirectionMapper {
    DirectionDto mapToDto(Direction direction);
    Direction mapToEntity(DirectionDto dto);
}
