package me.jaeyeopme.sns.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = DuplicatePhoneException.REASON)
public class DuplicatePhoneException extends RuntimeException {

    public static final String REASON = "The phone number is not available.";

}
