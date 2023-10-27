package org.example.service.impl;

import org.example.persistence.entity.Direction;
import org.example.persistence.repository.DirectionRepository;
import org.example.persistence.repository.TestRepository;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.example.service.mapper.DirectionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.example.util.DirectionTestData.*;
import static org.example.util.TestTestData.createTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        Direction direction = createAddedDirectionWithId();
        Direction updatedDirection = createUpdatedDIrection(createTest());
        DirectionResponseDto directionResponseDto = createResponseDirectionAfterUpdate();

        when(directionRepository.findById(1L)).thenReturn(Optional.of(direction));
        when(testRepository.findById(1L)).thenReturn(Optional.of(createTest()));
        when(directionRepository.save(any(Direction.class))).thenReturn(updatedDirection);
        when(directionMapper.fromEntityToResponseDto(updatedDirection)).thenReturn(directionResponseDto);

        DirectionResponseDto response = directionService.updateDirection(1L, requestDto);

        verify(directionRepository, times(1)).findById(1L);
        verify(testRepository, times(1)).findById(1L);
        verify(directionRepository, times(1)).save(direction);
        verify(directionMapper, times(1)).fromEntityToResponseDto(updatedDirection);

        assertEquals(requestDto.name(), response.name());
        assertEquals(requestDto.description(), response.description());
    }
}
