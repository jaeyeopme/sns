package me.jaeyeopme.sns.common.exception;

import lombok.Getter;
import me.jaeyeopme.sns.common.exception.dto.SNSErrorCode;

@Getter
public abstract class SNSException extends RuntimeException {

    private final SNSErrorCode code;

    public SNSException(final SNSErrorCode code) {
        super(code.message());
        this.code = code;
    }

    public SNSException(final SNSErrorCode code,
        final Throwable cause) {
        super(code.message(), cause);
        this.code = code;
    }

}
