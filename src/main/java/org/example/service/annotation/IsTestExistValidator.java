package org.example.service.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.api.controllers.exceptions.TestNotFoundException;
import org.example.service.TestService;

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
