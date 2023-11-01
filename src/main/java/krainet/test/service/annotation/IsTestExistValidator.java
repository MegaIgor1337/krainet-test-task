package krainet.test.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import krainet.test.service.TestService;
import lombok.RequiredArgsConstructor;
import krainet.test.api.exceptions.TestNotFoundException;

@RequiredArgsConstructor
public class IsTestExistValidator implements ConstraintValidator<IsTestExist, Long> {
    private final TestService testService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null || testService.isTestExist(id)) {
            return true;
        } else {
            throw new TestNotFoundException(String.format("Test with id %s not found", id));
        }
    }
}
