package me.jaeyeopme.sns.common.exception;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jaeyeopme.sns.common.exception.dto.SNSErrorCode;
import me.jaeyeopme.sns.common.exception.dto.SNSResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 비지니스 에러 예외 처리
     *
     * @param e {@link SNSException} 을 상속받은 예외
     * @return 예외 응답
     */
    @ExceptionHandler(SNSException.class)
    public ResponseEntity<SNSResponse<Void>> snsExceptionHandler(final SNSException e) {
        log.info("SNSException - Class Name: {}, Message: {}", e.getClass().getSimpleName(),
            e.getMessage());
        return SNSResponse.failure(e.code());
    }

    /**
     * 데이터 바인딩 에러 예외 처리
     *
     * @param e {@link ModelAttribute} 와 {@link Valid} 주석이 달린 인수에서 바인딩 예외
     * @return 바인딩 실패 필드를 포함한 BadRequest 예외 응답
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<SNSResponse<Void>> bindExceptionHandler(
        final BindException e) {
        log.info("BindException - Class Name: {}, Message: {}", e.getClass().getSimpleName(),
            e.getMessage());
        return SNSResponse.badRequest(e.getBindingResult());
    }

    /**
     * 데이터 바인딩 에러 예외 처리
     *
     * @param e {@link RequestBody}, {@link RequestPart} 를 제외한 {@link Valid} 주석이 달린 인수 바인딩 예외
     * @return 바인딩 실패 필드를 포함한 BadRequest 예외 응답
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<SNSResponse<Void>> constraintViolationExceptionHandler(
        final ConstraintViolationException e) {
        log.info("ConstraintViolationException - Class Name: {}, Message: {}",
            e.getClass().getSimpleName(), e.getMessage());
        return SNSResponse.badRequest(e.getConstraintViolations());
    }

    /**
     * 데이터 바인딩 에러 예외 처리
     *
     * @param e {@link RequestParam} 주석이 달린 인수에서 enum 바인딩 예외
     * @return BadRequest 예외 응답
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<SNSResponse<Void>> methodArgumentTypeMismatchExceptionHandler(
        final MethodArgumentTypeMismatchException e) {
        log.info("HttpRequestMethodNotSupportedException - Class Name: {}, Message: {}",
            e.getClass().getSimpleName(), e.getMessage());

        return SNSResponse.failure(SNSErrorCode.INVALID_ARGUMENT);
    }

    /**
     * HTTP Method 예외 처리
     *
     * @param e 지원하지 않은 HTTP Method 예외
     * @return MethodNotSupported 예외 응답
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<SNSResponse<Void>> httpRequestMethodNotSupportedExceptionHandler(
        HttpRequestMethodNotSupportedException e) {
        log.info("HttpRequestMethodNotSupportedException - Class Name: {}, Message: {}",
            e.getClass().getSimpleName(), e.getMessage());
        return SNSResponse.failure(SNSErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 예상하지 못한 예외 처리
     *
     * @param e 예상하지 못한 예외
     * @return InternalServerError 예외 응답
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<SNSResponse<Void>> exceptionHandler(final Exception e) {
        log.info("Exception - Class Name: {}, Message: {}",
            e.getClass().getSimpleName(), e.getMessage());
        return SNSResponse.failure(SNSErrorCode.INTERNAL_SERVER_ERROR);
    }

}
