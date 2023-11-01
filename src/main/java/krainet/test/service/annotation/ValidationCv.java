package krainet.test.service.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CvValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationCv {
    String message() default "Invalid cv document";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
