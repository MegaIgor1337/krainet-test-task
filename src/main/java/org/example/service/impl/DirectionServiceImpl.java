package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.repository.DirectionRepository;
import org.example.service.DirectionService;
import org.example.service.dto.DirectionDto;
import org.example.service.mapper.DirectionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectionServiceImpl implements DirectionService {
    private final DirectionRepository directionRepository;
    private final DirectionMapper directionMapper;
    @Override
    public List<DirectionDto> getDirections() {
        return directionRepository.findAll().stream()
                .map(directionMapper::mapToDto).toList();
    }
}
