package me.jaeyeopme.sns.user.presentation;

import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.exception.DuplicateEmailException;
import me.jaeyeopme.sns.common.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.common.exception.dto.SNSResponse;
import me.jaeyeopme.sns.user.application.UserFacade;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Phone;
import me.jaeyeopme.sns.user.presentation.dto.UserCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(UserRestController.URL)
@RestController
@RequiredArgsConstructor
public class UserRestController {

    public static final String URL = "/api/v1/users";

    private final UserFacade userFacade;

    /**
     * 회원 가입 API
     *
     * @param request 회원 가입 대상 정보
     * @throws DuplicateEmailException 이메일이 중복되는 경우
     * @throws DuplicatePhoneException 전화번호가 중복되는 경우
     */
    @PostMapping
    public ResponseEntity<SNSResponse<Void>> create(
        @RequestBody @Valid final UserCreateRequest request) {
        final var userId = userFacade.create(request);
        final var location = URI.create("%s/%s".formatted(URL, userId));

        return SNSResponse.create(location);
    }

    /**
     * 이메일 중복 검사 API
     *
     * @param email 검사 대상 이메일 정보
     * @throws DuplicateEmailException 이메일이 중복되는 경우
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<SNSResponse<Void>> existsByEmail(
        @PathVariable("email") @Valid Email email) {
        userFacade.verifyDuplicatedEmail(email);
        return SNSResponse.ok();
    }

    /**
     * 전화번호 중복 검사 API
     *
     * @param phone 검사 대상 전화번호 정보
     * @throws DuplicatePhoneException 전화번호가 중복되는 경우
     */
    @GetMapping("/phone/{phone}")
    public ResponseEntity<SNSResponse<Void>> existsByEmail(
        @PathVariable("phone") @Valid Phone phone) {
        userFacade.verifyDuplicatedPhone(phone);
        return SNSResponse.ok();
    }

}
