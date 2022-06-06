package me.jaeyeopme.sns.domain.user.api;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.application.AccountService;
import me.jaeyeopme.sns.domain.user.application.LoginService;
import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.exception.DuplicateEmailException;
import me.jaeyeopme.sns.domain.user.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.domain.user.exception.NotFoundEmailException;
import me.jaeyeopme.sns.domain.user.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.domain.user.record.UserCreateRequest;
import me.jaeyeopme.sns.domain.user.record.UserLoginRequest;
import me.jaeyeopme.sns.global.aop.LoginRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(AccountAPI.ACCOUNT_API_V1)
@RequiredArgsConstructor
@RestController
public class AccountAPI {

    public static final String ACCOUNT_API_V1 = "/api/v1/accounts";

    private final AccountService accountService;

    private final LoginService sessionLoginService;

    /**
     * 로그아웃
     */
    @LoginRequired
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        sessionLoginService.logout();
        return ResponseEntity.ok().build();
    }

    /**
     * 로그인 API
     *
     * @param request 로그인 대상 정보
     * @throws NotFoundEmailException      이메일이 존재하지 않는 경우
     * @throws NotMatchesPasswordException 비밀번호가 일치하지 않은 경우
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody final UserLoginRequest request) {
        sessionLoginService.login(request);
        return ResponseEntity.ok().build();
    }

    /**
     * 회원 가입 API
     *
     * @param request 회원 가입 대상 정보
     * @throws DuplicateEmailException 이메일이 중복되는 경우
     * @throws DuplicatePhoneException 전화번호가 중복되는 경우
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody final UserCreateRequest request) {
        final var userId = accountService.create(request);
        final var location = URI.create("%s/%s".formatted(ACCOUNT_API_V1, userId));

        return ResponseEntity.created(location).build();
    }

    /**
     * 이메일 중복 검사 API
     *
     * @param email 검사 대상 이메일
     * @throws DuplicateEmailException 이메일이 중복되는 경우
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Void> existsByEmail(@PathVariable("email") Email email) {
        accountService.verifyDuplicatedEmail(email);
        return ResponseEntity.ok().build();
    }

    /**
     * 전화번호 중복 검사 API
     *
     * @param phone 검사 대상 전화번호
     * @throws DuplicatePhoneException 전화번호가 중복되는 경우
     */
    @GetMapping("/phone/{phone}")
    public ResponseEntity<Void> existsByEmail(@PathVariable("phone") Phone phone) {
        accountService.verifyDuplicatedPhone(phone);
        return ResponseEntity.ok().build();
    }

}
