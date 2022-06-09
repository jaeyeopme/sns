package me.jaeyeopme.sns.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = InvalidSessionException.REASON)
public class InvalidSessionException extends RuntimeException {

    public static final String REASON = "This request requires a login.";

}
