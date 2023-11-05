package krainet.test.service.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsTestResultExistValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsTestResultExist {
    String message() default "Invalid test result id: test not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
