package krainet.test.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import krainet.test.api.exceptions.TestResultNotFoundException;
import krainet.test.service.TestResultService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsTestResultExistValidator implements ConstraintValidator<IsTestResultExist, Long> {
    private final TestResultService testResultService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (testResultService.isTestResultExist(id)) {
            return true;
        } else {
            throw new TestResultNotFoundException(String.format("Test with id %s not found", id));
        }
    }
}
