package me.jaeyeopme.sns.common.exception;

import me.jaeyeopme.sns.common.exception.dto.SNSErrorCode;

public class NotMatchesPasswordException extends SNSException {

    public NotMatchesPasswordException() {
        super(SNSErrorCode.NOT_MATCHES_PASSWORD);
    }

}
