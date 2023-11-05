package krainet.test.service.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = IsTestExistValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsTestExist {
    String message() default "Invalid test id: test not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
