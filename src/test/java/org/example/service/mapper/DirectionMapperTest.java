package org.example.service.mapper;

import org.example.persistence.entity.Direction;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.example.util.DirectionTestData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.example.util.DirectionTestData.createRequestDirection;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionMapperTest {

    private final DirectionMapper directionMapper = Mappers.getMapper(DirectionMapper.class);

    @Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {

        Direction entity = DirectionTestData.getAddedDirectionWithId();
        DirectionResponseDto responseDto = directionMapper.fromEntityToResponseDto(entity);

        assertEquals(entity.getDescription(), responseDto.description());
        assertEquals(entity.getName(), responseDto.name());
    }

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntity() {

        DirectionRequestDto requestDto = createRequestDirection();
        Direction entity = directionMapper.fromRequestDtoToEntity(requestDto);

        assertEquals(requestDto.name(), entity.getName());
        assertEquals(requestDto.description(), entity.getDescription());
    }
}
