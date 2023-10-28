package org.example.service.mapper;

import org.example.persistence.entity.Direction;
import org.example.persistence.entity.Test;
import org.example.persistence.repository.DirectionRepository;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestResponseDto;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {DirectionMapper.class})
public abstract class TestMapper {
    @Autowired
    protected DirectionRepository directionRepository;

    public abstract TestResponseDto fromEntityToResponseDto(Test test);

    public Test fromRequestDtoToEntity(TestRequestDto dto) {
        Test test = new Test();
        test.setName(dto.name());
        test.setDescription(dto.description());

        if (dto.directionsId() == null) {
            return test;
        }

        List<Direction> directions = dto.directionsId().stream()
                .map(directionRepository::findById)
                .map(d -> d.orElse(null)).toList();

        test.setDirections(directions);

        return test;
    }
}
