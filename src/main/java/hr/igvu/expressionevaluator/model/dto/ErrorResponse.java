package hr.igvu.expressionevaluator.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse<T> {
    private List<T> errors = new ArrayList<>(1);

    public ErrorResponse(List<T> errors) {
        this.errors = errors;
    }

    public List<T> getErrors() {
        return errors;
    }
}
