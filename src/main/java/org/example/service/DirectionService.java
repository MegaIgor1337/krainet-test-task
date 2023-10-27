package org.example.service;

import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;

import java.util.List;

public interface DirectionService {
    List<DirectionResponseDto> getDirections();
    DirectionResponseDto saveDirection(DirectionRequestDto directionRequestDto);
    DirectionResponseDto updateDirection(Long id, DirectionRequestDto directionRequestDto);
    boolean isDirectionExist(Long id);
}
