package me.jaeyeopme.sns.support;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;


public class ConstraintViolationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public <T> Set<ConstraintViolation<T>> validate(final T t) {
        return validator.validate(t);
    }

}
