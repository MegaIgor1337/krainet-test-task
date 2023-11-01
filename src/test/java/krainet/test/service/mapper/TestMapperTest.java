package krainet.test.service.mapper;

import krainet.test.persistence.entity.Direction;
import krainet.test.persistence.entity.Test;
import krainet.test.persistence.repository.DirectionRepository;
import krainet.test.service.dto.TestRequestDto;
import krainet.test.service.dto.TestResponseDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static krainet.test.util.DirectionTestData.createListOfDirections;
import static krainet.test.util.TestTestData.*;
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
    private TestMapper testMapper;

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

        TestResponseDto responseDto = testMapper.fromEntityToResponseDto(entity);

        assertEquals(entity.getDescription(), responseDto.description());
        assertEquals(entity.getName(), responseDto.name());
        assertEquals(entity.getDirections().size(), responseDto.directionsId().size());
        assertEquals(entity.getDirections().stream().toList().get(0).getId(),
                responseDto.directionsId().stream().toList().get(0));
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
