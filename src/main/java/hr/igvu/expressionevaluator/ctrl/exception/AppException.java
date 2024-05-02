package hr.igvu.expressionevaluator.ctrl.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AppException extends RuntimeException {

    public LocalDateTime time;
    public HttpStatus http;
    public String path;

    @Builder.Default
    public List<String> errors = new ArrayList<>();

}
