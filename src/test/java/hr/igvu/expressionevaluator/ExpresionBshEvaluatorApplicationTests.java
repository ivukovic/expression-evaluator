package hr.igvu.expressionevaluator;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.igvu.expressionevaluator.ctrl.ExpressionCtrl;
import hr.igvu.expressionevaluator.engine.BshEvaluator;
import hr.igvu.expressionevaluator.repo.ExpressionRepo;
import hr.igvu.expressionevaluator.srv.IExpressionSrv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExpresionBshEvaluatorApplicationTests {

    @Autowired
    private ExpressionCtrl exprCtrl;

    @Autowired
    private IExpressionSrv exprSrv;

    @Autowired
    private ExpressionRepo exprRepo;

    @Autowired
    private BshEvaluator bshEvaluator;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(exprCtrl);
        Assertions.assertNotNull(exprSrv);
        Assertions.assertNotNull(exprRepo);
        Assertions.assertNotNull(bshEvaluator);
        Assertions.assertNotNull(mapper);
    }
}
