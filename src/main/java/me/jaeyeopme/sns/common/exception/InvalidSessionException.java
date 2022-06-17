package me.jaeyeopme.sns.common.exception;

import me.jaeyeopme.sns.common.exception.dto.SNSErrorCode;

public class InvalidSessionException extends SNSException {

    public InvalidSessionException() {
        super(SNSErrorCode.INVALID_SESSION);
    }

}
