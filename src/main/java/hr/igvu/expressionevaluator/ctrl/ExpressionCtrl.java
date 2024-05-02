package hr.igvu.expressionevaluator.ctrl;

import hr.igvu.expressionevaluator.inf.validation.JSON;
import hr.igvu.expressionevaluator.inf.validation.UUID;
import hr.igvu.expressionevaluator.model.dto.EvaluationRes;
import hr.igvu.expressionevaluator.model.dto.ExpressionReq;
import hr.igvu.expressionevaluator.model.dto.ExpressionRes;
import hr.igvu.expressionevaluator.srv.IExpressionSrv;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@Log4j2
public class ExpressionCtrl {

    private final IExpressionSrv srv;

    @PostMapping(value = "/expression", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpressionRes> save(@RequestBody @Valid ExpressionReq req) {

        String uuid = srv.save(req);

        return ResponseEntity.ok(ExpressionRes.builder()
                .id(uuid)
                .build());
    }

    @PostMapping(value = "/evaluate/{expressionId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EvaluationRes> evaluate(
            @PathVariable @Validated @NotBlank @Size(min = 36, max = 36) @UUID String expressionId,
            @RequestBody @Validated @NotBlank @Size(max = 500) @JSON String json) {

        Object eval = srv.evaluate(expressionId, json);

        return ResponseEntity.ok(EvaluationRes.builder()
                .evaluation(eval)
                .build());
    }
}
