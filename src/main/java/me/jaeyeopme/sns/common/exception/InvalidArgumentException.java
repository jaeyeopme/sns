package me.jaeyeopme.sns.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = InvalidArgumentException.REASON)
public class InvalidArgumentException extends RuntimeException {

    public static final String REASON = "The value is not available.";

}
