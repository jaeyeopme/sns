package me.jaeyeopme.sns.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = NotMatchesPasswordException.REASON)
public class NotMatchesPasswordException extends RuntimeException {

    public static final String REASON = "The password is not available.";

}
