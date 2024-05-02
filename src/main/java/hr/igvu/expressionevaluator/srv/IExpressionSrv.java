package hr.igvu.expressionevaluator.srv;

import hr.igvu.expressionevaluator.model.dto.ExpressionReq;

public interface IExpressionSrv {

    public String save(ExpressionReq expressions);

    public Object evaluate(String uuid, String json);
}
