package org.example.service.impl;

import org.example.persistence.entity.Direction;
import org.example.persistence.repository.DirectionRepository;
import org.example.service.DirectionService;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.example.service.mapper.DirectionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.util.DirectionTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DirectionServiceImplTest {
    @InjectMocks
    private DirectionServiceImpl directionService;

    @Mock
    private DirectionMapper directionMapper;

    @Mock
    private DirectionRepository directionRepository;

    @Test
    public void testAddDirectionSuccess() {
        DirectionRequestDto validDirectionRequestDto = createRequestDirection();
        Direction directionWithoutId = getAddedDirectionWithoutId();
        Direction directionWithId = getAddedDirectionWithId();
        DirectionResponseDto expectedDto = createResponseDirection();

        when(directionMapper.fromRequestDtoToEntity(validDirectionRequestDto)).thenReturn(directionWithoutId);
        when(directionRepository.save(directionWithoutId)).thenReturn(directionWithId);
        when(directionMapper.fromEntityToResponseDto(directionWithId)).thenReturn(expectedDto);

        DirectionResponseDto responseDto = directionService.addDirection(validDirectionRequestDto);

        assertNotNull(responseDto);
        assertEquals(validDirectionRequestDto.name(), responseDto.name());
        assertEquals(validDirectionRequestDto.description(), responseDto.description());
    }
}
