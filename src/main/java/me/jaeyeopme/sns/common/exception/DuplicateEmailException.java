package me.jaeyeopme.sns.common.exception;

import me.jaeyeopme.sns.common.exception.dto.SNSErrorCode;

public class DuplicateEmailException extends SNSException {

    public DuplicateEmailException() {
        super(SNSErrorCode.DUPLICATE_EMAIL);
    }

}
