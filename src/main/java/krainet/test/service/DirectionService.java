package krainet.test.service;

import krainet.test.service.dto.DirectionResponseDto;
import krainet.test.service.dto.PageRequestDto;
import krainet.test.service.dto.DirectionRequestDto;

import java.util.List;

public interface DirectionService {
    List<DirectionResponseDto> getDirections(String name, PageRequestDto pageRequestDto);

    DirectionResponseDto saveDirection(DirectionRequestDto directionRequestDto);

    DirectionResponseDto updateDirection(Long id, DirectionRequestDto directionRequestDto);

    boolean isDirectionExist(Long id);
}
