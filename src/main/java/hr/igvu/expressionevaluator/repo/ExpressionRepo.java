package hr.igvu.expressionevaluator.repo;

import hr.igvu.expressionevaluator.model.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ExpressionRepo extends JpaRepository<Expression, Long> {
    @Query("select e from Expression e where e.uuid = :uuid")
    Optional<Expression> findByUIID(@Param("uuid") String uuid);
}
