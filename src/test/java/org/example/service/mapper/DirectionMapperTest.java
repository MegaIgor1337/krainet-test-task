package org.example.service.mapper;

import org.example.persistence.entity.Direction;
import org.example.service.TestService;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.example.util.DirectionTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.util.DirectionTestData.createRequestDirection;
import static org.example.util.DirectionTestData.createRequestDirectionWithTest;
import static org.example.util.TestTestData.createTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DirectionMapperTest {
    @Mock
    private TestService testService;
    @InjectMocks
    private final DirectionMapper directionMapper = Mappers.getMapper(DirectionMapper.class);

    @Test
    public void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {

        Direction entity = DirectionTestData.createDirectionWithoutId();
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


    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromDtoToEntityWithTest() {
        DirectionRequestDto requestDto = createRequestDirectionWithTest();

        when(testService.findTestById(requestDto.testId())).thenReturn(createTest());

        Direction entity = directionMapper.fromRequestDtoToEntity(requestDto);

        assertEquals(requestDto.name(), entity.getName());
        assertEquals(createTest().getName(), entity.getTest().getName());
        assertEquals(requestDto.description(), entity.getDescription());
    }
}
