package hr.igvu.expressionevaluator.engine;

import bsh.EvalError;
import bsh.Interpreter;
import hr.igvu.expressionevaluator.ctrl.exception.AppException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Log4j2
public class BshEvaluator {

    public Object evaluate(String expression, Map<String, Object> dataMap) {
        Interpreter interpreter = new Interpreter();

        try {
            log.debug("START populating interpreter");
            populateInterpreter(interpreter, dataMap, null);
            log.debug("END  populating interpreter");
            Object ret = interpreter.eval(expression);
            log.debug("Interpreter evaluated={} for expression={}", ret, expression);
            return ret;
        } catch (EvalError e) {
            log.error("Error evaluating expression={}", expression, e);
            throw AppException.builder()
                    .time(LocalDateTime.now())
                    .http(INTERNAL_SERVER_ERROR)
                    .errors(List.of("Error evaluating expression.", e.getMessage()))
                    .build();
        }
    }

    private void populateInterpreter(Interpreter interpreter, Map dataMap, String objPrefix) throws EvalError {
        for (Object key : dataMap.keySet()) {
            String prefKey = objPrefix == null ? key.toString() : (objPrefix + "." + key.toString());
            Object val = dataMap.get(key.toString());
            if (val instanceof Map) {
                populateInterpreter(interpreter, (Map) val, prefKey);
            } else {
                if (val instanceof String) {
                    val = ((String) val).intern();
                }
                interpreter.set(prefKey, val);
                log.debug("    adding to interpreter Key={}, Value={}", prefKey, val);
            }
        }
    }

    public static void main(String[] args) {
        BshEvaluator e = new BshEvaluator();

//        String exp = "(customer.firstName == JOHN && customer.salary < 100) || (customer.address != null && customer.address.city == Chicago)";
        String exp = "(customer.firstName == \"JOHN\" AND customer.salary < 100) OR (customer.address != null AND customer.address.city == \"Washington\")";
//        String exp = "not_null != null";


        exp = StringUtils.replaceIgnoreCase(exp, " OR ", " @or ");
        exp = StringUtils.replaceIgnoreCase(exp, " AND ", " @and ");

        System.out.println("Expression : " + exp);

        Interpreter interpreter = new Interpreter();

        try {
            interpreter.set("customer.firstName", "JOHN");
            interpreter.set("customer.salary", 99);
            //interpreter.set("customer.address", "NOT_EMPTY");
            interpreter.set("customer.address.city", "Chicago");


            Object eval = interpreter.eval(exp);

            System.out.println("Expression value: " + eval);
        } catch (EvalError ex) {
            throw new RuntimeException(ex);
        }


    }
}
