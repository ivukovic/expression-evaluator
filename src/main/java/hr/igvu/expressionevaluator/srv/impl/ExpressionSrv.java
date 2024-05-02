package hr.igvu.expressionevaluator.srv.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.igvu.expressionevaluator.ctrl.exception.AppException;
import hr.igvu.expressionevaluator.engine.BshEvaluator;
import hr.igvu.expressionevaluator.model.dto.ExpressionReq;
import hr.igvu.expressionevaluator.model.entity.Expression;
import hr.igvu.expressionevaluator.repo.ExpressionRepo;
import hr.igvu.expressionevaluator.srv.IExpressionSrv;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
@Log4j2
public class ExpressionSrv implements IExpressionSrv {

    private final ExpressionRepo exprRepo;
    private final BshEvaluator bshEvaluator;
    private final ObjectMapper mapper;

    @Override
    public String save(ExpressionReq req) {

        String uuid = UUID.nameUUIDFromBytes(req.getName().getBytes()).toString();
        log.debug("Created UUID={} from expression name={}", uuid, req.getName());

        Optional<Expression> dbExp = exprRepo.findByUIID(uuid);
        if (dbExp.isPresent()) {
            log.debug("Entity with same UUID={} already present in database", uuid);
            throw AppException.builder()
                    .time(LocalDateTime.now())
                    .http(CONFLICT)
                    .errors(List.of("Expression with same name=<" + req.getName() + "> already exist"))
                    .build();
        }

        Expression exp = exprRepo.save(populateExpression(req, uuid));

        return uuid;
    }

    @Override
    public Object evaluate(String uuid, String json) {
        Optional<Expression> e = exprRepo.findByUIID(uuid);

        if (e.isEmpty()) {
            log.error("Entity not found for UUID={}", uuid);

            throw AppException.builder()
                    .time(LocalDateTime.now())
                    .http(NOT_FOUND)
                    .errors(List.of("Expression not found for give UUID=" + uuid))
                    .build();
        }

        Map<String, Object> map = jsonToMap(json);

        return bshEvaluator.evaluate(e.get().getExpVal(), map);
    }


    private Map<String, Object> jsonToMap(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            @SuppressWarnings({"unchecked"})
            Map map = mapper.convertValue(node, Map.class);
            return map;
        } catch (JsonProcessingException ex) {
            log.error("Error validating input JSON={}", json, ex);
            throw AppException.builder()
                    .time(LocalDateTime.now())
                    .http(BAD_REQUEST)
                    .errors(List.of("Input is nit a  valid JSON string.", ex.getMessage()))
                    .build();
        }
    }

    private Expression populateExpression(ExpressionReq req, String uuid) {

        Expression e = new Expression();
        e.setName(req.getName());
        e.setExpVal(prepareExpression(req.getValue()));
        e.setUuid(uuid);

        return e;
    }

    private String prepareExpression(String exp) {
        exp = StringUtils.replaceIgnoreCase(exp, " OR ", " @or ");
        exp = StringUtils.replaceIgnoreCase(exp, " AND ", " @and ");
        return exp;
    }
}