package me.jaeyeopme.sns.common.exception;

import me.jaeyeopme.sns.common.exception.dto.SNSErrorCode;

public class NotFoundEmailException extends SNSException {

    public NotFoundEmailException() {
        super(SNSErrorCode.NOT_FOUND_EMAIL);
    }

}
