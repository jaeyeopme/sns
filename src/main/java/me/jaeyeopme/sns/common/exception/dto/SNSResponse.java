package me.jaeyeopme.sns.common.exception.dto;

import java.net.URI;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SNSResponse<T> {

    private Result result;

    private T data;

    private String code;

    private String reason;

    private List<SNSFieldError> errors;

    public static ResponseEntity<SNSResponse<Void>> ok() {
        final var body = SNSResponse.<Void>builder()
            .result(Result.SUCCESS)
            .build();

        return ResponseEntity.ok(body);
    }

    public static ResponseEntity<SNSResponse<Void>> create(final URI location) {
        final var body = SNSResponse.<Void>builder()
            .result(Result.SUCCESS)
            .build();

        return ResponseEntity.created(location).body(body);
    }

    public static ResponseEntity<SNSResponse<Void>> create() {
        final var body = SNSResponse.<Void>builder()
            .result(Result.SUCCESS)
            .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static <T> ResponseEntity<SNSResponse<T>> success(final HttpStatus status,
        final T data) {
        final var body = SNSResponse.<T>builder()
            .result(Result.SUCCESS)
            .data(data)
            .build();

        return ResponseEntity.status(status).body(body);
    }

    public static ResponseEntity<SNSResponse<Void>> badRequest(final BindingResult bindingResult) {
        final var body = SNSResponse.<Void>builder()
            .result(Result.FAILURE)
            .code(SNSErrorCode.INVALID_ARGUMENT.code())
            .reason(SNSErrorCode.INVALID_ARGUMENT.message())
            .errors(SNSFieldError.of(bindingResult))
            .build();

        return ResponseEntity.badRequest().body(body);
    }

    public static ResponseEntity<SNSResponse<Void>> badRequest(
        final Set<ConstraintViolation<?>> constraintViolations) {
        final var body = SNSResponse.<Void>builder()
            .result(Result.FAILURE)
            .code(SNSErrorCode.INVALID_ARGUMENT.code())
            .reason(SNSErrorCode.INVALID_SESSION.message())
            .errors(SNSFieldError.of(constraintViolations))
            .build();

        return ResponseEntity.badRequest().body(body);
    }

    public static ResponseEntity<SNSResponse<Void>> failure(final SNSErrorCode errorCode) {
        final var body = SNSResponse.<Void>builder()
            .result(Result.FAILURE)
            .code(errorCode.code())
            .reason(errorCode.message())
            .build();

        return ResponseEntity.status(errorCode.status()).body(body);
    }

    private enum Result {
        SUCCESS, FAILURE
    }

}
