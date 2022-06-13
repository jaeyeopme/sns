package me.jaeyeopme.sns.unit.session.presentation;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import me.jaeyeopme.sns.common.exception.InvalidSessionException;
import me.jaeyeopme.sns.common.exception.NotFoundEmailException;
import me.jaeyeopme.sns.common.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.session.presentation.SessionRestController;
import me.jaeyeopme.sns.support.restdocs.RestDocsTestSupport;
import me.jaeyeopme.sns.support.user.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionRestControllerTest extends RestDocsTestSupport {

    @SneakyThrows
    @DisplayName("이메일이 존재하지 않은 경우 실패하고 HTTP 404를 반환한다.")
    @Test
    void 세션_생성_실패_이메일이_존재하지_않은_경우() {
        // GIVEN
        final var request = UserFixture.SESSION_CREATE_REQUEST;
        willThrow(NotFoundEmailException.class).given(sessionFacade).create(request);

        // WHEN
        final var when = performPost(SessionRestController.URL, request);

        // THEN
        when.andExpectAll(status().isNotFound(),
            status().reason(NotFoundEmailException.REASON));
        then(sessionFacade).should(only()).create(request);
    }

    @SneakyThrows
    @DisplayName("비밀번호가 일치하지 않은 경우 실패하고 HTTP 401을 반환한다.")
    @Test
    void 세션_생성_실패_비밀번호가_일치하지_않은_경우() {
        // GIVEN
        final var request = UserFixture.SESSION_CREATE_REQUEST;
        willThrow(NotMatchesPasswordException.class).given(sessionFacade).create(request);

        // WHEN
        final var when = performPost(SessionRestController.URL, request);

        // THEN
        when.andExpectAll(status().isUnauthorized(),
            status().reason(NotMatchesPasswordException.REASON));

        then(sessionFacade).should(only()).create(request);
    }

    @SneakyThrows
    @DisplayName("입력 값이 올바른 경우 HTTP 201을 반환한다.")
    @Test
    void 세션_생성_성공() {
        // GIVEN
        final var request = UserFixture.SESSION_CREATE_REQUEST;

        // WHEN
        final var when = performPost(SessionRestController.URL, request);

        // THEN
        when.andExpectAll(status().isCreated());
        then(sessionFacade).should(only()).create(request);
    }

    @SneakyThrows
    @DisplayName("유효하지 않은 세션인 경우 실패하고 HTTP 401을 반환한다.")
    @Test
    void 세션_만료_실패_유효하지_않은_세션인_경우() {
        // GIVEN
        willThrow(InvalidSessionException.class).given(sessionFacade).getAccount();

        // WHEN
        final var when = performDelete(SessionRestController.URL);

        // THEN
        when.andExpectAll(status().isUnauthorized());
        then(sessionFacade).should(only()).getAccount();
    }

    @SneakyThrows
    @DisplayName("유효한 세션인 경우 성공하고 HTTP 200을 반환한다.")
    @Test
    void 세션_만료_성공() {
        // GIVEN
        final var principal = UserFixture.PRINCIPAL;
        given(sessionFacade.getAccount()).willReturn(principal);

        // WHEN
        final var when = performDelete(SessionRestController.URL);

        // THEN
        when.andExpectAll(status().isOk());
        then(sessionFacade).should().getAccount();
        then(sessionFacade).should().invalidate();
    }


}
