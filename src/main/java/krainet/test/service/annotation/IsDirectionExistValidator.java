package krainet.test.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import krainet.test.api.exceptions.DirectionNotFoundException;
import krainet.test.service.DirectionService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsDirectionExistValidator implements ConstraintValidator<IsDirectionExist, Long> {
    private final DirectionService directionService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id != null && directionService.isDirectionExist(id)) {
            return true;
        } else {
            throw new DirectionNotFoundException(String.format("Direction with id %s not found", id));
        }
    }
}
