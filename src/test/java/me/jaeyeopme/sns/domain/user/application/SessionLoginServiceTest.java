package me.jaeyeopme.sns.domain.user.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import me.jaeyeopme.sns.domain.fixture.UserFixture;
import me.jaeyeopme.sns.domain.user.application.impl.SessionLoginService;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.exception.NotFoundEmailException;
import me.jaeyeopme.sns.domain.user.exception.NotMatchesPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class SessionLoginServiceTest {

    @InjectMocks
    private SessionLoginService loginService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @Mock
    private HttpSession httpSession;

    @DisplayName("로그인 시")
    @Nested
    public class When_Login {

        @DisplayName("이메일이 존재하지 않은 경우 실패한다.")
        @Test
        void Given_NotExistsEmail_When_Login_Then_SaveSession() {
            // GIVEN
            final var request = UserFixture.USER_LOGIN_REQUEST;
            given(userRepository.findByAccountEmailValue(request.email().getValue()))
                .willReturn(Optional.empty());

            // WHEN
            final Executable when = () -> loginService.login(request);

            // THEN
            assertThrows(NotFoundEmailException.class, when);
            then(userRepository).should(only())
                .findByAccountEmailValue(request.email().getValue());
        }

        @DisplayName("비밀번호가 일치하지 않은 경우 실패한다.")
        @Test
        void Given_NotMatchesPassword_When_Login_Then_SaveSession() {
            // GIVEN
            final var request = UserFixture.USER_LOGIN_REQUEST;
            final var user = UserFixture.USER;
            given(userRepository.findByAccountEmailValue(request.email().getValue()))
                .willReturn(Optional.of(user));
            given(passwordEncryptor.matches(request.password(), user.getAccount().getPassword()))
                .willReturn(Boolean.FALSE);

            // WHEN
            final Executable when = () -> loginService.login(request);

            // THEN
            assertThrows(NotMatchesPasswordException.class, when);
            then(userRepository).should()
                .findByAccountEmailValue(request.email().getValue());
            then(passwordEncryptor).should()
                .matches(request.password(), user.getAccount().getPassword());
            then(httpSession).should(never())
                .setAttribute(anyString(), any());
        }

        @DisplayName("입력 값이 올바른 세션을 등록하고 경우 성공한다.")
        @Test
        void Given_CorrectInput_When_Login_Then_SaveSession() {
            // GIVEN
            final var request = UserFixture.USER_LOGIN_REQUEST;
            final var user = UserFixture.USER;
            ReflectionTestUtils.setField(user, "id", 1L);
            given(userRepository.findByAccountEmailValue(request.email().getValue()))
                .willReturn(Optional.of(user));
            given(passwordEncryptor.matches(request.password(), user.getAccount().getPassword()))
                .willReturn(Boolean.TRUE);

            // WHEN
            loginService.login(request);

            // THEN
            then(userRepository).should()
                .findByAccountEmailValue(request.email().getValue());
            then(passwordEncryptor).should()
                .matches(request.password(), user.getAccount().getPassword());
            then(httpSession).should().setAttribute(SessionLoginService.SESSION_NAME, user.getId());
        }

    }

}