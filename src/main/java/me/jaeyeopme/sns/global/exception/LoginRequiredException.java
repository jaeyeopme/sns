package me.jaeyeopme.sns.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = LoginRequiredException.REASON)
public class LoginRequiredException extends RuntimeException {

    public static final String REASON = "This request requires a login.";

}
