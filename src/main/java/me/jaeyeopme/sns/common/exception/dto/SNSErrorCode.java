package me.jaeyeopme.sns.common.exception.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SNSErrorCode {

    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "FORM-400",
        "잘못된 요청 값입니다."),

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "REQUEST-405",
        "지원하지 않는 HTTP Method 입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER-500",
        "서버측 오류가 발생했습니다."),

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "SIGNUP-409",
        "중복된 이메일 입니다."),

    DUPLICATE_PHONE(HttpStatus.CONFLICT, "SIGNUP-409",
        "중복된 전화번호 입니다."),

    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "LOGIN-404",
        "존재하지 않는 이메일 입니다."),

    NOT_MATCHES_PASSWORD(HttpStatus.UNAUTHORIZED, "LOGIN-401",
        "일치하지 않는 비밀번호 입니다."),

    INVALID_SESSION(HttpStatus.UNAUTHORIZED, "SESSION-401",
        "로그인이 필요한 요청입니다.");

    private final HttpStatus status;

    private final String code;

    private final String message;

}
