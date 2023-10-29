package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.entity.Direction;
import org.example.persistence.repository.DirectionRepository;
import org.example.persistence.repository.TestRepository;
import org.example.service.DirectionService;
import org.example.service.dto.DirectionRequestDto;
import org.example.service.dto.DirectionResponseDto;
import org.example.service.dto.PageRequestDto;
import org.example.service.mapper.DirectionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.service.util.PageUtil.getPageable;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectionServiceImpl implements DirectionService {
    private final DirectionRepository directionRepository;
    private final DirectionMapper directionMapper;
    private final TestRepository testRepository;


    @Override
    public List<DirectionResponseDto> getDirections(String name, PageRequestDto pageRequestDto) {

        log.info("Get list of directions on service method with name - {}, page number - {}, page size - {}",
                name, pageRequestDto.pageNumber(), pageRequestDto.pageNumber());

        Pageable pageable = getPageable(pageRequestDto, directionRepository.getCountOfDirections());

        Page<Direction> directionPage = getPageOfDirections(name, pageable);

        return directionPage.map(directionMapper::fromEntityToResponseDto).getContent();
    }

    private Page<Direction> getPageOfDirections(String name, Pageable pageable) {
        if (name != null) {
            return directionRepository.findAllByName(name, pageable);
        } else {
            return directionRepository.findAll(pageable);
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
