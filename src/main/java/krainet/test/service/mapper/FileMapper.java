package krainet.test.service.mapper;

import krainet.test.service.dto.FileDto;
import krainet.test.persistence.entity.File;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FileMapper {
    FileDto fromEntityToResponseDto(File file);
}
