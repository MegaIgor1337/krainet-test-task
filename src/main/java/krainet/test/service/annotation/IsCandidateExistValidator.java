package krainet.test.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import krainet.test.service.CandidateService;
import lombok.RequiredArgsConstructor;
import krainet.test.api.exceptions.CandidateNotFoundException;

@RequiredArgsConstructor
public class IsCandidateExistValidator implements ConstraintValidator<IsCandidateExist, Long> {
    private final CandidateService candidateService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (candidateService.isCandidateExist(id)) {
            return true;
        } else {
            throw new CandidateNotFoundException(String.format("Candidate with id %s not found", id));
        }
    }
}
