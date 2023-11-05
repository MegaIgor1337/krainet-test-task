package krainet.test.service.impl;

import krainet.test.persistence.entity.Direction;
import krainet.test.persistence.repository.DirectionRepository;
import krainet.test.service.DirectionService;
import krainet.test.service.dto.DirectionRequestDto;
import krainet.test.service.dto.DirectionResponseDto;
import krainet.test.service.dto.PageRequestDto;
import krainet.test.service.mapper.DirectionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static krainet.test.service.util.PageUtil.getPageable;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DirectionServiceImpl implements DirectionService {
    private final DirectionRepository directionRepository;
    private final DirectionMapper directionMapper;

    @Override
    public List<DirectionResponseDto> getDirections(String name, PageRequestDto pageRequestDto) {

        log.info("Get list of directions on service method with name - {}, page {}",
                name,pageRequestDto);

        Pageable pageable = getPageable(pageRequestDto, directionRepository.getCountOfDirections(), null);
        Specification<Direction> directionSpecification = getSpecifications(name);

        Page<Direction> directionPage = directionRepository.findAll(directionSpecification, pageable);

        return directionPage.map(directionMapper::fromEntityToResponseDto).getContent();
    }


    @Override
    @Transactional
    public DirectionResponseDto saveDirection(DirectionRequestDto directionRequestDto) {
        Direction direction = directionMapper
                .fromRequestDtoToEntity(directionRequestDto);
        Direction savedDirection = directionRepository.save(direction);
        log.info("Saved direction - {}", savedDirection);
        return directionMapper.fromEntityToResponseDto(savedDirection);
    }

    @Override
    @Transactional
    public DirectionResponseDto updateDirection(Long id, DirectionRequestDto directionRequestDto) {
        Direction directionRequest = directionMapper.fromRequestDtoToEntity(directionRequestDto);
        directionRequest.setId(id);
        Direction updatedDirection = directionRepository.save(directionRequest);
        log.info("Updated direction - {}", updatedDirection);
        return directionMapper.fromEntityToResponseDto(updatedDirection);
    }


    @Override
    public boolean isDirectionExist(Long id) {
        return directionRepository.existsById(id);
    }

    private Specification<Direction> getSpecifications(String name) {
        Specification<Direction> specification = Specification.where(null);
        if (name != null && !name.isBlank()) {
            specification = specification.and(((root, query, cb) ->
                    cb.equal(root.get("name"), name)));
        }
        return specification;
    }

}
