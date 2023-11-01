package krainet.test.service.util;

import lombok.experimental.UtilityClass;
import krainet.test.service.dto.PageRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PageUtil {
    public static Pageable getPageable(PageRequestDto pageRequestDto, Integer countOfEntities) {
        Integer pageSize = pageRequestDto.pageSize();
        Integer pageNumber = pageRequestDto.pageNumber();

        if (pageSize == null) {
            pageSize = countOfEntities;
        }
        if (pageNumber == null) {
            pageNumber = 0;
        }

        return PageRequest.of(pageNumber, pageSize);
    }
}
