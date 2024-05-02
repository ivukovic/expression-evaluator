package hr.igvu.expressionevaluator.ctrl.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        String error = ex.getParameterName() + " parameter is missing.";
//        return new ResponseEntity<Object>(new ErrorResponse(List.of(error)) {
//        }, HttpStatus.BAD_REQUEST);
//    }
//
//    /**
//     * Exception thrown when {@link org.springframework.validation.annotation.Validated} is used in controller.
//     *
//     * @param ex
//     * @param request
//     * @return
//     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
//        try {
//            List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
//            return new ResponseEntity<>(new hr.igvu.expressionevaluator.model.dto.ErrorResponse<>(messages), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ErrorResponse<>(List.of(ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    /**
//     * @param ex
//     * @param request
//     * @return
//     */
//    @ExceptionHandler(EntityNotFoundException.class)
//    protected ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
//        try {
//            return new ResponseEntity<>(new ErrorResponse<>(List.of(ex.getMessage())), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ErrorResponse<>(List.of(ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
