package krainet.test.service.util;

import krainet.test.service.dto.SortFields;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class SortUtil {
    public static <T extends SortFields> Sort getSort(List<T> sortCandidateFields, Sort.Direction direction) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sortCandidateFields != null && !sortCandidateFields.isEmpty()) {
            for (T sortField : sortCandidateFields) {
                orders.add(new Sort.Order(direction, sortField.getEntityFieldName()));
            }
        }
        return Sort.by(orders);
    }
}
