package hr.igvu.expressionevaluator.engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.igvu.expressionevaluator.ctrl.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ExpressionTest {

    @Autowired
    private BshEvaluator bshEvaluator;

    @Autowired
    private ObjectMapper mapper;

    static Map<String, Object> JSON;

    @BeforeAll
    static void setUp() throws IOException {
        JSON = loadJsonMap();
    }


    @Test
    void evaluateSimpleTernary() {
        String expression = "1>1 ? true : false";

        boolean evaluationResult = (boolean) bshEvaluator.evaluate(expression, JSON);

        Assertions.assertFalse(evaluationResult);
    }

    @Test
    void evaluateErrorTernary() {
        String expression = "1=1 ? true : false";

        assertThrows(AppException.class, () -> bshEvaluator.evaluate(expression, JSON));
    }

    @Test
    void evaluateSimpleTernary2() {
        String expression = "1==1 ? true : false";

        boolean evaluationResult = (boolean) bshEvaluator.evaluate(expression, JSON);

        Assertions.assertTrue(evaluationResult);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/expression.csv", numLinesToSkip = 1)
    void evaluateDataWithExpression_multipleComplicatedExpressionWithData(String expression, boolean expected) {

        boolean evaluationResult = (boolean) bshEvaluator.evaluate(prepareExpression(expression), JSON);

        Assertions.assertEquals(expected, evaluationResult);
    }

    private String prepareExpression(String exp) {
        exp = StringUtils.replaceIgnoreCase(exp, " OR ", " @or ");
        exp = StringUtils.replaceIgnoreCase(exp, " AND ", " @and ");
        return exp;
    }

    static Map<String, Object> loadJsonMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File evaluationDataFile = new ClassPathResource("data.json").getFile();
        byte[] byteArray = Files.readAllBytes(evaluationDataFile.toPath());
        String json = new String(byteArray);
        JsonNode node = mapper.readTree(json);
        @SuppressWarnings({"unchecked"})
        Map map = mapper.convertValue(node, Map.class);

        return map;
    }
}
