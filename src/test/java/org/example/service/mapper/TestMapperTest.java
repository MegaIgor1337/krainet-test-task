package org.example.service.mapper;

import org.example.persistence.entity.Direction;
import org.example.persistence.entity.Test;
import org.example.persistence.repository.DirectionRepository;
import org.example.service.dto.DirectionResponseDto;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestResponseDto;
import org.example.util.DirectionTestData;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.example.util.DirectionTestData.createListOfDirections;
import static org.example.util.TestTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestMapperTest {
    @Mock
    private DirectionMapper directionMapper;
    @Mock
    private DirectionRepository directionRepository;
    @InjectMocks
    private final TestMapper testMapper = Mappers.getMapper(TestMapper.class);

    @org.junit.jupiter.api.Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDtoWithoutDirections() {
        Test entity = createTestWithoutDirections();
        TestResponseDto responseDto = testMapper.fromEntityToResponseDto(entity);

        assertEquals(entity.getDescription(), responseDto.description());
        assertEquals(entity.getName(), responseDto.name());
    }

    @org.junit.jupiter.api.Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDtoWitDirections() {
        Test entity = createTestWithDirections();
        List<DirectionResponseDto> directionResponseDtos = DirectionTestData
                .createListOfDirectionResponseDto();

        when(directionMapper.fromEntityToResponseDto(any(Direction.class)))
                .thenReturn(directionResponseDtos.get(0),
                        directionResponseDtos.get(1));

        TestResponseDto responseDto = testMapper.fromEntityToResponseDto(entity);

        assertEquals(entity.getDescription(), responseDto.description());
        assertEquals(entity.getName(), responseDto.name());
        assertEquals(entity.getDirections().size(), responseDto.directions().size());
        assertEquals(entity.getDirections().stream().toList().get(0).getName(),
                responseDto.directions().stream().toList().get(0).name());
    }

    @org.junit.jupiter.api.Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntityWithoutDirections() {
        TestRequestDto requestDto = createRequestTestWithoutDirections();

        Test entity = testMapper.fromRequestDtoToEntity(requestDto);

        assertEquals(requestDto.name(), entity.getName());
        assertEquals(requestDto.description(), entity.getDescription());
    }


    @org.junit.jupiter.api.Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntityWithDirections() {
        TestRequestDto requestDto = createRequestTestWithDirections();
        List<Direction> directions = createListOfDirections();

        when(directionRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(directions.get(0)),
                        Optional.of(directions.get(1)));

        Test entity = testMapper.fromRequestDtoToEntity(requestDto);

        assertEquals(requestDto.name(), entity.getName());
        assertEquals(requestDto.description(), entity.getDescription());
        assertEquals(requestDto.directionsId().size(), entity.getDirections().size());
    }


}
