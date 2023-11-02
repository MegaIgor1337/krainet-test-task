package krainet.test.service.util;

import lombok.experimental.UtilityClass;
import krainet.test.service.dto.PageRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageUtil {
    public static Pageable getPageable(PageRequestDto pageRequestDto, Integer countOfEntities, Sort sort) {
        Integer pageSize = pageRequestDto.pageSize();
        Integer pageNumber = pageRequestDto.pageNumber();

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
