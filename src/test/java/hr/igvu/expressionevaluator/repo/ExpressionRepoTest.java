package hr.igvu.expressionevaluator.repo;

import hr.igvu.expressionevaluator.model.entity.Expression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureDataJpa
public class ExpressionRepoTest {

    @Autowired
    ExpressionRepo repo;

    @Test
    void saveExpression() {

        Expression e = new Expression();
        e.setName("Expression");
        e.setExpVal("1>1 ? true : false");
        e.setUuid(UUID.nameUUIDFromBytes("Expression".getBytes()).toString());

        Expression eDb = repo.save(e);

        Assertions.assertNotNull(eDb.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveExpressionInvalidName(String name) {

        Expression e = new Expression();
        e.setName(name);
        e.setExpVal("1>1 ? true : false");
        e.setUuid(UUID.nameUUIDFromBytes("Expression".getBytes()).toString());

        TransactionSystemException transactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> {
            Expression eDb = repo.save(e);
        });

        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("Name of expression must not be blank"));
    }

    @Test
    void saveExpressionInvalidNameLength() {

        Expression e = new Expression();
        e.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in");
        e.setExpVal("1>1 ? true : false");
        e.setUuid(UUID.nameUUIDFromBytes("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in".getBytes()).toString());

        TransactionSystemException transactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> {
            Expression eDb = repo.save(e);
        });

        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("Max size for name of expression is 50"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void saveExpressionInvalidExpressionValue(String expVal) {

        Expression e = new Expression();
        e.setName("Expression");
        e.setExpVal(expVal);
        e.setUuid(UUID.nameUUIDFromBytes("Expression".getBytes()).toString());

        TransactionSystemException transactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> {
            Expression eDb = repo.save(e);
        });

        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("Expression evaluation must not be blank"));
    }

    @Test
    void saveExpressionInvalidExpressionValueLengthMax() {

        Expression e = new Expression();
        e.setName("Expression");
        e.setExpVal("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Vel facilisis volutpat est velit egestas dui id ornare. Risus quis varius quam quisque. Nibh praesent tristique magna sit amet purus gravida quis blandit. Cursus turpis massa tincidunt dui ut ornare lectus sit. Est sit amet facilisis magna etiam. Commodo sed egestas egestas fringilla phasellus. Interdum posuere lorem ipsum dolor sit amet consectetur. Tincidunt nunc pulvinar sapien et ligula. Ullamcorper malesuada proin libero nunc consequat interdum varius. Elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at. Pharetra");
        e.setUuid(UUID.nameUUIDFromBytes("Expression".getBytes()).toString());

        TransactionSystemException transactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> {
            Expression eDb = repo.save(e);
        });

        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("Max size for expression evaluation is 500"));
    }

    @Test
    void saveExpressionInvalidExpressionValueLengthMin() {

        Expression e = new Expression();
        e.setName("Expression");
        e.setExpVal("Lor");
        e.setUuid(UUID.nameUUIDFromBytes("Expression".getBytes()).toString());

        TransactionSystemException transactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> {
            Expression eDb = repo.save(e);
        });

        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("Min size for expression evaluation is 4"));
    }

    @Test
    void saveExpressionInvalidExpressionUUID() {

        Expression e = new Expression();
        e.setName("Expression");
        e.setExpVal("1>1 ? true : false");
        e.setUuid("D952EB9F-7AD2-4B1B-B3CE-38673520599");

        TransactionSystemException transactionSystemException = Assertions.assertThrows(TransactionSystemException.class, () -> {
            Expression eDb = repo.save(e);
        });

        Assertions.assertTrue(transactionSystemException.getRootCause().toString().contains("Must be a valid UUID UUID format defined by RFC 4122 -->  xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"));
    }


    @Test
    @Transactional
    void loadExpression() {


        String uuid = UUID.nameUUIDFromBytes("Expression".getBytes()).toString();

        Expression e = new Expression();
        e.setName("Expression");
        e.setExpVal("1>1 ? true : false");
        e.setUuid(UUID.nameUUIDFromBytes("Expression".getBytes()).toString());

        Expression savedExp = repo.save(e);
        Long savedId = savedExp.getId();


        Optional<Expression> loadedExp = repo.findById(savedId);

        Assertions.assertTrue(loadedExp.isPresent());
        Assertions.assertEquals(savedId, loadedExp.get().getId());
        Assertions.assertEquals("Expression", loadedExp.get().getName());
        Assertions.assertEquals("1>1 ? true : false", loadedExp.get().getExpVal());
        Assertions.assertEquals(uuid, loadedExp.get().getUuid());
    }
}
