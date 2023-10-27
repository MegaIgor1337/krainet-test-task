package org.example.service.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsDirectionExistValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsDirectionExist {
    String message() default "Invalid direction id: direction not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
