package me.jaeyeopme.sns.common.exception;

import me.jaeyeopme.sns.common.exception.dto.SNSErrorCode;

public class DuplicatePhoneException extends SNSException {

    public DuplicatePhoneException() {
        super(SNSErrorCode.DUPLICATE_PHONE);
    }

}
