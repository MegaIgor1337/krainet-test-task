package krainet.test.service.mapper;

import krainet.test.persistence.entity.File;
import krainet.test.service.dto.FileDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static krainet.test.util.FileTestData.getDocument;
import static krainet.test.util.FileTestData.getImage;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FileMapperTest {
    private final FileMapper fileMapper = Mappers.getMapper(FileMapper.class);

    @Test
    public void shouldCorrectMapImageEntityInDto() throws IOException {
        File file = getImage();

        FileDto fileDto = fileMapper.fromEntityToResponseDto(file);

        assertEquals(file.getName(), fileDto.name());
        assertEquals(file.getId(), fileDto.id());
    }

    @Test
    public void shouldCorrectMapPdfFileEntityInDto() throws IOException {
        File file = getDocument();

        FileDto fileDto = fileMapper.fromEntityToResponseDto(file);

        assertEquals(file.getName(), fileDto.name());
        assertEquals(file.getId(), fileDto.id());
    }
}
