package krainet.test.service.util;

import krainet.test.service.dto.PageRequestDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageUtil {
    public static Pageable getPageable(PageRequestDto pageRequestDto, Integer countOfEntities, Sort sort) {
        Integer pageSize = pageRequestDto != null ? pageRequestDto.pageSize() : null;
        Integer pageNumber = pageRequestDto != null ? pageRequestDto.pageNumber() : null;

        if (pageSize == null) {
            pageSize = countOfEntities;
        }
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (sort != null) {
            return PageRequest.of(pageNumber, pageSize, sort);
        }

        return PageRequest.of(pageNumber, pageSize);
    }
}
