package me.jaeyeopme.sns.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = DuplicateEmailException.REASON)
public class DuplicateEmailException extends RuntimeException {

    public static final String REASON = "The email is not available.";

}
