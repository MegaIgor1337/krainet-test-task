package org.example.service.impl;

import org.example.persistence.entity.Direction;
import org.example.persistence.repository.DirectionRepository;
import org.example.persistence.repository.TestRepository;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.example.service.dto.PageRequestDto;
import org.example.service.mapper.DirectionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.example.util.DirectionTestData.*;
import static org.example.util.TestTestData.createTestWithoutDirections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DirectionServiceImplTest {
    @InjectMocks
    private DirectionServiceImpl directionService;

    @Mock
    private DirectionMapper directionMapper;
    @Mock
    private TestRepository testRepository;

    @Mock
    private DirectionRepository directionRepository;

    @Test
    public void testAddDirectionSuccess() {
        DirectionRequestDto validDirectionRequestDto = createRequestDirection();
        Direction directionWithoutId = createDirectionWithoutId();
        Direction directionWithId = createAddedDirectionWithId();
        DirectionResponseDto expectedDto = createResponseDirection();

        when(directionMapper.fromRequestDtoToEntity(validDirectionRequestDto)).thenReturn(directionWithoutId);
        when(directionRepository.save(directionWithoutId)).thenReturn(directionWithId);
        when(directionMapper.fromEntityToResponseDto(directionWithId)).thenReturn(expectedDto);

        DirectionResponseDto responseDto = directionService.saveDirection(validDirectionRequestDto);

        assertNotNull(responseDto);
        assertEquals(validDirectionRequestDto.name(), responseDto.name());
        assertEquals(validDirectionRequestDto.description(), responseDto.description());
    }

    @Test
    public void testUpdateDirectionSuccess() {
        DirectionRequestDto requestDto = createRequestForUpdateDirectionWithTest();
        Direction directionWithoutId = createDirectionWithoutIdWithTest(createTestWithoutDirections());
        Direction updatedDirection = createUpdatedDIrection(createTestWithoutDirections());
        DirectionResponseDto directionResponseDto = createResponseDirectionAfterUpdate();

        when(directionMapper.fromRequestDtoToEntity(requestDto)).thenReturn(directionWithoutId);
        when(directionRepository.save(directionWithoutId)).thenReturn(updatedDirection);
        when(directionMapper.fromEntityToResponseDto(updatedDirection)).thenReturn(directionResponseDto);

        DirectionResponseDto response = directionService.updateDirection(1L, requestDto);

        verify(directionMapper, times(1)).fromRequestDtoToEntity(requestDto);
        verify(directionRepository, times(1)).save(updatedDirection);
        verify(directionMapper, times(1)).fromEntityToResponseDto(updatedDirection);

        assertEquals(requestDto.name(), response.name());
        assertEquals(requestDto.description(), response.description());
    }


    @Test
    public void testGetDirectionsWithNameAndPageSize() {
        List<DirectionResponseDto> expectedDirections = createListOfDirectionResponseDto();
        List<Direction> directions = createListOfDirections();

        when(directionRepository.findAllByName(DIRECTION_NAME, PageRequest.of(0, 2)))
                .thenReturn(new PageImpl<>(directions));
        when(directionMapper.fromEntityToResponseDto(any(Direction.class)))
                .thenReturn(expectedDirections.get(0),
                        expectedDirections.get(1));

        List<DirectionResponseDto> directionResponseDtos = directionService
                .getDirections(DIRECTION_NAME, new PageRequestDto(0, 2));

        assertEquals(expectedDirections.size(), directionResponseDtos.size());
        assertEquals(expectedDirections.get(0).name(), directionResponseDtos.get(0).name());
        assertEquals(expectedDirections.size(), directionResponseDtos.size());
    }


    @Test
    public void testGetTrueWhenDirectionExist() {
        when(directionRepository.existsById(1L)).thenReturn(true);

        boolean result = directionService.isDirectionExist(1L);

        assertTrue(result);
    }
}
