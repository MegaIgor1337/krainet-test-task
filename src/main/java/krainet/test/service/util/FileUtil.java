package krainet.test.service.util;

import krainet.test.persistence.entity.File;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@UtilityClass
public class FileUtil {
    public static File createFile(MultipartFile multipartFile) throws IOException {
        return File.builder()
                .name(multipartFile.getOriginalFilename())
                .content(multipartFile.getBytes()).build();
    }
}
