package hr.igvu.expressionevaluator.inf.validation.validator;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.igvu.expressionevaluator.ctrl.exception.AppException;
import hr.igvu.expressionevaluator.inf.validation.JSON;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
public class JsonValidator implements ConstraintValidator<JSON, String> {


    @Override
    public boolean isValid(String json, ConstraintValidatorContext context) {
        // check if it is a valid JSON String
        boolean ret = true;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(json);
        } catch (JacksonException jex) {
            log.error("Error validating input JSON={}", json, jex);
            ret = false;
            throw AppException.builder()
                    .time(LocalDateTime.now())
                    .http(BAD_REQUEST)
                    .errors(List.of("Input is not a valid JSON string.", jex.getMessage()))
                    .build();

        }
        return ret;
    }
}
