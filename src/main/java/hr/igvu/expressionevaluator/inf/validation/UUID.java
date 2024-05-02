package hr.igvu.expressionevaluator.inf.validation;


import hr.igvu.expressionevaluator.inf.validation.validator.UUIDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UUIDValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UUID {
    String message() default "Must be a valid UUID in format defined by RFC 4122 -->  xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (32 hexadecimal digits grouped into five sections separated by hyphens)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}