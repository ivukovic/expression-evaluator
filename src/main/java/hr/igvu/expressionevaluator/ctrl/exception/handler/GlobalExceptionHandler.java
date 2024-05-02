package hr.igvu.expressionevaluator.ctrl.exception.handler;

import hr.igvu.expressionevaluator.ctrl.exception.AppException;
import hr.igvu.expressionevaluator.inf.Util;
import hr.igvu.expressionevaluator.model.error.AppErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppErrorResponse> handleAppException(AppException ex, HttpServletRequest request) {

        AppErrorResponse error = AppErrorResponse.builder()
                .errors(ex.errors)
                .timestamp(Util.formatISO8601(ex.time))
                .status(ex.getHttp().value())
                .title(ex.getHttp().getReasonPhrase())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, ex.getHttp());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        AppErrorResponse ret = AppErrorResponse.builder()
                .errors(errors)
                .timestamp(Util.formatLocalDatetimeISO8601())
                .status(BAD_REQUEST.value())
                .title(BAD_REQUEST.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(ret, BAD_REQUEST);
    }
}




