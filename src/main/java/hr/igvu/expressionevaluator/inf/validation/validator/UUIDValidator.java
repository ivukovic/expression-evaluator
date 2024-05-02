package hr.igvu.expressionevaluator.inf.validation.validator;

import hr.igvu.expressionevaluator.ctrl.exception.AppException;
import hr.igvu.expressionevaluator.inf.validation.UUID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
public class UUIDValidator implements ConstraintValidator<UUID, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // check if it is a valid UUID
        boolean ret = true;
        try {
            java.util.UUID.fromString(value);
        } catch (IllegalArgumentException iae) {
            log.error("Error validating UUID={}", value, iae);
            ret = false;
            throw AppException.builder()
                    .time(LocalDateTime.now())
                    .http(BAD_REQUEST)
                    .errors(List.of(value + " Must be a valid UUID. Example: 123e4567-e89b-42d3-a456-556642440000."))
                    .build();
        }
        return ret;
    }
}
