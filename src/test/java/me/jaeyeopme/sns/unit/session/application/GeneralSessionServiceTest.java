package me.jaeyeopme.sns.unit.session.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;

import java.util.Optional;
import me.jaeyeopme.sns.common.exception.InvalidSessionException;
import me.jaeyeopme.sns.session.application.service.GeneralSessionService;
import me.jaeyeopme.sns.session.domain.repository.SessionRepository;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneralSessionServiceTest {

    @InjectMocks
    private GeneralSessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @DisplayName("세션 생성 시")
    @Nested
    public class When_Create {

        @DisplayName("세션을 저장한다.")
        @Test
        void Then_ReturnPrincipal() {
            // GIVEN
            final var principal = UserFixture.PRINCIPAL;

            // WHEN
            sessionService.create(principal);

            // THEN
            then(sessionRepository).should(only()).create(principal);
        }

    }

    @DisplayName("세션 만료 시")
    @Nested
    public class When_Invalidate {

        @DisplayName("유효한 세션인 경우 성공한다.")
        @Test
        void Given_ValidSession_Then_DoNothing() {
            // WHEN
            sessionService.invalidate();

            // THEN
            then(sessionRepository).should(only()).invalidate();
        }

    }

    @DisplayName("세션 조회 시")
    @Nested
    public class When_GetSessionPrincipal {

        @DisplayName("유효하지 않은 세션인 경우 실패한다.")
        @Test
        void Given_InvalidSession_Then_ThrowException() {
            // GIVEN
            willThrow(InvalidSessionException.class).given(sessionRepository).principal();

            // WHEN
            final Executable when = () -> sessionService.principal();

            // THEN
            assertThrows(InvalidSessionException.class, when);
            then(sessionRepository).should(only()).principal();
        }

        @DisplayName("유효한 세션인 경우 성공한다.")
        @Test
        void Given_ValidSession_Then_ReturnPrincipal() {
            // GIVEN
            final var expected = Optional.of(UserFixture.PRINCIPAL);
            given(sessionRepository.principal()).willReturn(expected);

            // WHEN
            final var actual = sessionService.principal();

            // THEN
            assertThat(actual).isEqualTo(expected.get());
            then(sessionRepository).should(only()).principal();
        }

    }

}