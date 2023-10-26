package org.example.service.mapper;

import org.example.persistence.entity.Direction;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DirectionMapper {
    DirectionResponseDto fromEntityToResponseDto(Direction direction);
    Direction fromRequestDtoToEntity(DirectionRequestDto dto);
}
