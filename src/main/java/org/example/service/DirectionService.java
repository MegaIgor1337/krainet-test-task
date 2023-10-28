package org.example.service;

import org.example.persistence.entity.Direction;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;

import java.util.List;

public interface DirectionService {
    List<DirectionResponseDto> getDirections(String name, Integer pageSize, Integer pageNumber);

    DirectionResponseDto saveDirection(DirectionRequestDto directionRequestDto);

    DirectionResponseDto updateDirection(Long id, DirectionRequestDto directionRequestDto);

    boolean isDirectionExist(Long id);
}
