package hr.igvu.expressionevaluator.inf.validation;


import hr.igvu.expressionevaluator.inf.validation.validator.JsonValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = JsonValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JSON {
    String message() default "Must be a valid JSON string.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}