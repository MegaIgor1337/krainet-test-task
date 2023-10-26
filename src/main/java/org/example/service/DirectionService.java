package org.example.service;

import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;

import java.util.List;

public interface DirectionService {
    List<DirectionResponseDto> getDirections();
    DirectionResponseDto addDirection(DirectionRequestDto directionRequestDto);
}
