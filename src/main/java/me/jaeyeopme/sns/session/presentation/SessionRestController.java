package me.jaeyeopme.sns.session.presentation;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.aop.SessionRequired;
import me.jaeyeopme.sns.common.exception.NotFoundEmailException;
import me.jaeyeopme.sns.common.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.session.application.SessionFacade;
import me.jaeyeopme.sns.session.presentation.dto.SessionCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(SessionRestController.URL)
@RequiredArgsConstructor
@RestController
public class SessionRestController {

    public static final String URL = "/api/v1/sessions";

    private final SessionFacade sessionFacade;

    /**
     * 로그인 API
     *
     * @param request 로그인 대상 정보
     * @throws NotFoundEmailException      이메일이 존재하지 않는 경우
     * @throws NotMatchesPasswordException 비밀번호가 일치하지 않은 경우
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody final SessionCreateRequest request) {
        sessionFacade.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그아웃 API
     */
    @SessionRequired
    @DeleteMapping
    public ResponseEntity<Void> invalidate() {
        sessionFacade.invalidate();
        return ResponseEntity.ok().build();
    }

}
