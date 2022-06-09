package me.jaeyeopme.sns.domain.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import me.jaeyeopme.sns.domain.fixture.UserFixture;
import me.jaeyeopme.sns.domain.user.application.impl.HttpSessionService;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.exception.InvalidSessionException;
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

@ExtendWith(MockitoExtension.class)
class HttpSessionServiceTest {

    @InjectMocks
    private HttpSessionService sessionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptor encryptor;

    @Mock
    private HttpSession session;

    @DisplayName("세션 생성 시")
    @Nested
    public class When_Create {

        @DisplayName("이메일이 존재하지 않은 경우 실패한다.")
        @Test
        void Given_NotExistsEmail_When_Create_Then_ThrowException() {
            // GIVEN
            final var request = UserFixture.SESSION_CREATE_REQUEST;
            given(userRepository.findByAccountEmailValue(request.email().getValue()))
                .willReturn(Optional.empty());

            // WHEN
            final Executable when = () -> sessionService.create(request);

            // THEN
            assertThrows(NotFoundEmailException.class, when);
            then(userRepository).should(only())
                .findByAccountEmailValue(request.email().getValue());
        }

        @DisplayName("비밀번호가 일치하지 않은 경우 실패한다.")
        @Test
        void Given_NotMatchesPassword_When_Create_Then_ThrowException() {
            // GIVEN
            final var request = UserFixture.SESSION_CREATE_REQUEST;
            final var user = UserFixture.USER;
            given(userRepository.findByAccountEmailValue(request.email().getValue()))
                .willReturn(Optional.of(user));
            given(encryptor.matches(request.password(), user.getAccount().getPassword()))
                .willReturn(Boolean.FALSE);

            // WHEN
            final Executable when = () -> sessionService.create(request);

            // THEN
            assertThrows(NotMatchesPasswordException.class, when);
            then(userRepository).should()
                .findByAccountEmailValue(request.email().getValue());
            then(encryptor).should()
                .matches(request.password(), user.getAccount().getPassword());
            then(session).should(never())
                .setAttribute(HttpSessionService.SESSION_NAME, user.getAccount());
        }

        @DisplayName("입력 값이 올바른 경우 성공한다.")
        @Test
        void Given_CorrectInput_When_Create_Then_ReturnAccount() {
            // GIVEN
            final var request = UserFixture.SESSION_CREATE_REQUEST;
            final var user = UserFixture.USER;
            given(userRepository.findByAccountEmailValue(request.email().getValue()))
                .willReturn(Optional.of(user));
            given(encryptor.matches(request.password(), user.getAccount().getPassword()))
                .willReturn(Boolean.TRUE);

            // WHEN
            sessionService.create(request);

            // THEN
            then(userRepository).should()
                .findByAccountEmailValue(request.email().getValue());
            then(encryptor).should()
                .matches(request.password(), user.getAccount().getPassword());
            then(session).should()
                .setAttribute(HttpSessionService.SESSION_NAME, user.getAccount());
        }

    }

    @DisplayName("세션 만료 시")
    @Nested
    public class When_Invalidate {

        @DisplayName("유효한 세션인 경우 성공한다.")
        @Test
        void Given_ValidSession_When_Invalidate_Then_DoNothing() {
            // GIVEN

            // WHEN
            sessionService.invalidate();

            // THEN
            then(session).should(only()).invalidate();
        }

    }

    @DisplayName("세션 조회 시")
    @Nested
    public class When_GetInfo {

        @DisplayName("유효하지 않은 세션인 경우 실패한다.")
        @Test
        void Given_InvalidSession_When_GetInfo_Then_ThrowException() {
            // GIVEN
            given(session.getAttribute(HttpSessionService.SESSION_NAME)).willReturn(null);

            // WHEN
            final Executable when = () -> sessionService.getInfo();

            // THEN
            assertThrows(InvalidSessionException.class, when);
            then(session).should(only()).getAttribute(HttpSessionService.SESSION_NAME);
        }

        @DisplayName("유효한 세션인 경우 성공한다.")
        @Test
        void Given_ValidSession_When_GetInfo_Then_ReturnAccount() {
            // GIVEN
            final var expected = UserFixture.ACCOUNT;
            given(session.getAttribute(HttpSessionService.SESSION_NAME)).willReturn(expected);

            // WHEN
            final var actual = sessionService.getInfo();

            // THEN
            assertThat(actual).isEqualTo(expected);
            then(session).should(only()).getAttribute(HttpSessionService.SESSION_NAME);
        }

    }

}