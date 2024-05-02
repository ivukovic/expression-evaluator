package hr.igvu.expressionevaluator.ctrl;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.igvu.expressionevaluator.ExpresionEvaluatorApplication;
import hr.igvu.expressionevaluator.model.dto.EvaluationRes;
import hr.igvu.expressionevaluator.model.dto.ExpressionReq;
import hr.igvu.expressionevaluator.model.dto.ExpressionRes;
import hr.igvu.expressionevaluator.model.error.AppErrorResponse;
import hr.igvu.expressionevaluator.srv.IExpressionSrv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ExpresionEvaluatorApplication.class)
@AutoConfigureMockMvc
class ExpressionCtrlTest {

    public static final String TEST_JSON = "{\"name\":\"Igor\",\"address\":{\"city\":\"Zagreb\"},\"zip\":\"10000\"}";

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private IExpressionSrv exprSrv;

    @Test
    void saveValid() throws Exception {
        ExpressionReq expressionRequest = ExpressionReq.builder()
                .name("Test2")
                .value("true ? 1 == 1 : 1 > 2")
                .build();

        String expressionRequestAsJson = objectMapper.writeValueAsString(expressionRequest);

        ResultActions resultActions = mockMvc.perform(post("/expression")
                .contentType(APPLICATION_JSON)
                .content(expressionRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, ExpressionRes.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveInvalidName(String name) throws Exception {
        ExpressionReq expressionRequest = ExpressionReq.builder()
                .name(name)
                .value("true ? 1 == 1 : 1 > 2")
                .build();
        String expressionRequestAsJson = objectMapper.writeValueAsString(expressionRequest);

        ResultActions resultActions = mockMvc.perform(post("/expression")
                .contentType(APPLICATION_JSON)
                .content(expressionRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, AppErrorResponse.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveInvalidExpressionValue(String expression) throws Exception {
        ExpressionReq expressionRequest = ExpressionReq.builder()
                .name("Test")
                .value(expression)
                .build();
        String expressionRequestAsJson = objectMapper.writeValueAsString(expressionRequest);

        ResultActions resultActions = mockMvc.perform(post("/expression")
                .contentType(APPLICATION_JSON)
                .content(expressionRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, AppErrorResponse.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveInvalidAllParams(String value) throws Exception {
        ExpressionReq expressionRequest = ExpressionReq.builder()
                .name(value)
                .value(value)
                .build();
        String expressionRequestAsJson = objectMapper.writeValueAsString(expressionRequest);

        ResultActions resultActions = mockMvc.perform(post("/expression")
                .contentType(APPLICATION_JSON)
                .content(expressionRequestAsJson));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, AppErrorResponse.class));
    }

    @Test
    void evaluateValid() throws Exception {

        ExpressionReq req = ExpressionReq.builder()
                .name("Test")
                .value("true ? 1 == 1 : 1 > 2")
                .build();

        String uuid = exprSrv.save(req);

        ResultActions resultActions;
        resultActions = mockMvc.perform(post("/evaluate/" + uuid)
                .contentType(APPLICATION_JSON)
                .content(TEST_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
        MvcResult mvcResult = resultActions
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, EvaluationRes.class));
        EvaluationRes evaluationResponse = objectMapper.readValue(responseBody, EvaluationRes.class);
        Assertions.assertTrue((Boolean) evaluationResponse.evaluation);
    }


    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"6699-466-8595", "some text", "23434321-6699-466-8595-e21e21313", "dummy"})
    void evaluateInvalidUUID(String id) throws Exception {

        ResultActions resultActions = mockMvc.perform(post("/evaluate/" + id)
                .contentType(APPLICATION_JSON)
                .content(TEST_JSON));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, AppErrorResponse.class));
    }

    @ParameterizedTest
    @EmptySource
    void evaluateLogicalExpression_emptyEvaluationData(String data) throws Exception {
        String id = UUID.randomUUID().toString();

        ResultActions resultActions = mockMvc.perform(post("/evaluate/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(data));

        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(isValidJson(responseBody, AppErrorResponse.class));
    }

    private boolean isValidJson(String json, Class clazz) {
        try {
            objectMapper.readValue(json, clazz);
        } catch (JacksonException e) {
            return false;
        }
        return true;
    }
}