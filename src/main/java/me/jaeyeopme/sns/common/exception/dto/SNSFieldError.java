package me.jaeyeopme.sns.common.exception.dto;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SNSFieldError {

    private String field;

    private String value;

    private String reason;

    public static List<SNSFieldError> of(final BindingResult errors) {
        final var fieldErrors = errors.getFieldErrors();
        return fieldErrors.stream()
            .map(SNSFieldError::map)
            .toList();
    }

    public static List<SNSFieldError> of(final Set<ConstraintViolation<?>> errors) {
        return errors.stream()
            .map(SNSFieldError::map)
            .toList();
    }

    private static SNSFieldError map(final FieldError error) {
        return new SNSFieldError(
            error.getField().split("\\.")[0],
            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
            error.getDefaultMessage());
    }

    private static SNSFieldError map(final ConstraintViolation<?> error) {
        return new SNSFieldError(
            error.getPropertyPath().toString(),
            error.getInvalidValue() != null ? "" : error.getInvalidValue().toString(),
            error.getMessage());
    }

}
