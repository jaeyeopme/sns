package me.jaeyeopme.sns.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = NotFoundEmailException.REASON)
public class NotFoundEmailException extends RuntimeException {

    public static final String REASON = "The email address is not found.";

}
