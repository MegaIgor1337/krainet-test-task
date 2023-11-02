package krainet.test.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortCandidateFields {
    ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    DIRECTIONS_ID("directions.id"),
    DIRECTIONS_NAME("directions.name");
    private final String entityFieldName;
}
