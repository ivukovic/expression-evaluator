package hr.igvu.expressionevaluator.model.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "EXPRESSION")
@NoArgsConstructor
@Data
@Valid
public class Expression implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name of expression must not be blank")
    @Size(max = 50, message = "Max size for name of expression is 50")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Expression evaluation must not be blank")
    @Size(max = 500, message = "Max size for expression evaluation is 500")
    @Size(min = 4, message = "Min size for expression evaluation is 4")
    private String expVal;

    @Column(nullable = false)
    @NotBlank(message = "UUID must not be blank")
    @Size(min = 36, max = 36, message = "Must be a valid UUID UUID format defined by RFC 4122 -->  xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")
    private String uuid;
}
