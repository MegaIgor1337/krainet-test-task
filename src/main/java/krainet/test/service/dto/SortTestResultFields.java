package krainet.test.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortTestResultFields implements SortFields {
    ID("id"),
    DATE("date"),
    SCORE("score"),
    CANDIDATE_ID("candidate.id"),
    CANDIDATE_FIRST_NAME("candidate.firstName"),
    CANDIDATE_LAST_NAME("candidate.firstName");
    private final String entityFieldName;
}
