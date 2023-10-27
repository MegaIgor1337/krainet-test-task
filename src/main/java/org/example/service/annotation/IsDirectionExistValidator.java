package org.example.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.api.controllers.exceptions.DirectionNotFoundException;
import org.example.service.DirectionService;

@RequiredArgsConstructor
public class IsDirectionExistValidator implements ConstraintValidator<IsDirectionExist, Long> {
    private final DirectionService directionService;
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (directionService.isDirectionExist(id)) {
            return true;
        } else {
            throw new DirectionNotFoundException(String.format("Direction with id %s not found", id));
        }
    }
}
