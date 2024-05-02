package hr.igvu.expressionevaluator.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpressionReq {

    @NotNull
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @NotNull
    @Size(max = 500, message = "Max length for expression is 500 characters")
    @Size(min = 4, message = "Min length for expression is 4 characters")
    private String value;
}
