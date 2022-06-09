package me.jaeyeopme.sns.unit.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;

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
        void When_Create_Then_ReturnAccount() {
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
        void Given_ValidSession_When_Invalidate_Then_DoNothing() {
            // GIVEN

            // WHEN
            sessionService.invalidate();

            // THEN
            then(sessionRepository).should(only()).invalidate();
        }

    }

    @DisplayName("세션 조회 시")
    @Nested
    public class When_GetInfo {

        @DisplayName("유효하지 않은 세션인 경우 실패한다.")
        @Test
        void Given_InvalidSession_When_GetInfo_Then_ThrowException() {
            // GIVEN
            final var account = UserFixture.ACCOUNT;
            willThrow(InvalidSessionException.class).given(sessionRepository).getPrincipal();

            // WHEN
            final Executable when = () -> sessionService.getPrincipal();

            // THEN
            assertThrows(InvalidSessionException.class, when);
            then(sessionRepository).should(only()).getPrincipal();
        }

        @DisplayName("유효한 세션인 경우 성공한다.")
        @Test
        void Given_ValidSession_When_GetInfo_Then_ReturnAccount() {
            // GIVEN
            final var expected = UserFixture.PRINCIPAL;
            given(sessionRepository.getPrincipal()).willReturn(expected);

            // WHEN
            final var actual = sessionService.getPrincipal();

            // THEN
            assertThat(actual).isEqualTo(expected);
            then(sessionRepository).should(only()).getPrincipal();
        }

    }

}