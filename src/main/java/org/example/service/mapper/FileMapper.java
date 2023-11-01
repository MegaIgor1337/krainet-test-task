package org.example.service.mapper;

import org.example.persistence.entity.File;
import org.example.service.dto.FileDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FileMapper {
    FileDto fromEntityToResponseDto(File file);
}
