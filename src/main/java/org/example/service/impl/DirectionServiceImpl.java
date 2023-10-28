package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.entity.Direction;
import org.example.persistence.repository.DirectionRepository;
import org.example.persistence.repository.TestRepository;
import org.example.service.DirectionService;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.example.service.mapper.DirectionMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final TestRepository testRepository;


    @Override
    public List<DirectionResponseDto> getDirections(String name, Integer pageNumber, Integer pageSize) {
        log.info("Get list of directions on service method with name - {}, page number - {}, page size - {}",
                name, pageNumber, pageSize);
        pageSize = pageSize != null ? pageSize : directionRepository.getCountOfDirections();
        if (name != null && pageSize != 0) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return directionRepository.findAllByName(name, pageable)
                    .map(directionMapper::fromEntityToResponseDto).getContent();
        } else if (name == null && pageSize != 0) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return directionRepository.findAll(pageable)
                    .map(directionMapper::fromEntityToResponseDto).getContent();
        } else if (name != null) {
            return directionRepository.findAllByName(name).stream()
                    .map(directionMapper::fromEntityToResponseDto).toList();
        } else {
            return directionRepository.findAll().stream()
                    .map(directionMapper::fromEntityToResponseDto).toList();
        }
    }

    @Override
    @Transactional
    public DirectionResponseDto saveDirection(DirectionRequestDto directionRequestDto) {
        Direction direction = directionMapper
                .fromRequestDtoToEntity(directionRequestDto);
        Direction savedDirection = directionRepository.save(direction);
        log.info("saved direction - {}", savedDirection);
        return directionMapper.fromEntityToResponseDto(savedDirection);
    }

    @Override
    @Transactional
    public DirectionResponseDto updateDirection(Long id, DirectionRequestDto directionRequestDto) {
        Direction directionRequest = directionMapper.fromRequestDtoToEntity(directionRequestDto);
        directionRequest.setId(id);
        Direction updatedDirection = directionRepository.save(directionRequest);
        log.info("updated direction - {}", updatedDirection);
        return directionMapper.fromEntityToResponseDto(updatedDirection);
    }


    @Override
    public boolean isDirectionExist(Long id) {
        return directionRepository.existsById(id);
    }
}
