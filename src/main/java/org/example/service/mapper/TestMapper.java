package org.example.service.mapper;

import lombok.RequiredArgsConstructor;
import org.example.persistence.entity.Direction;
import org.example.persistence.entity.Test;
import org.example.persistence.repository.DirectionRepository;
import org.example.service.dto.TestRequestDto;
import org.example.service.dto.TestResponseDto;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@RequiredArgsConstructor
public class TestMapper {

    protected final DirectionRepository directionRepository;

    public  TestResponseDto fromEntityToResponseDto(Test test) {
        List<Long> directionsId = null;

        if (test.getDirections() != null) {
            directionsId = test.getDirections().stream()
                    .map(Direction::getId).toList();
        }

        return TestResponseDto.builder()
                .id(test.getId())
                .name(test.getName())
                .description(test.getDescription())
                .directionsId(directionsId).build();

    }

    public Test fromRequestDtoToEntity(TestRequestDto dto) {
        Test test = Test.builder()
                .name(dto.name())
                .description(dto.description())
                .build();

        if (dto.directionsId() == null) {
            test.setDirections(null);
            return test;
        }

        List<Direction> directions = dto.directionsId().stream()
                .map(directionRepository::findById)
                .map(d -> d.orElse(null)).toList();

        test.setDirections(directions);

        return test;
    }
}
