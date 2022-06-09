package me.jaeyeopme.sns.unit.user.presentation;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.jaeyeopme.sns.common.aop.SessionRequiredAspect;
import me.jaeyeopme.sns.common.exception.InvalidSessionException;
import me.jaeyeopme.sns.common.exception.NotFoundEmailException;
import me.jaeyeopme.sns.common.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.session.application.SessionFacade;
import me.jaeyeopme.sns.session.presentation.SessionAPI;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Import({AopAutoConfiguration.class, SessionRequiredAspect.class})
@WebMvcTest(SessionAPI.class)
public class SessionAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionFacade sessionFacade;

    @SneakyThrows
    private ResultActions performPost(final Object content) {
        return mockMvc.perform(
                post(SessionAPI.V1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(content)))
            .andDo(print());
    }

    @SneakyThrows
    private ResultActions performDelete() {
        return mockMvc.perform(
                delete(SessionAPI.V1))
            .andDo(print());
    }

    @DisplayName("세션 생성 시")
    @Nested
    public class When_Create {

        @SneakyThrows
        @DisplayName("이메일이 존재하지 않은 경우 실패하고 HTTP 404를 반환한다.")
        @Test
        void Given_NotExistsEmail_When_Create_Then_HTTP404() {
            // GIVEN
            final var request = UserFixture.SESSION_CREATE_REQUEST;
            willThrow(NotFoundEmailException.class).given(sessionFacade).create(request);

            // WHEN
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isNotFound(),
                status().reason(NotFoundEmailException.REASON));
            then(sessionFacade).should(only()).create(request);
        }

        @SneakyThrows
        @DisplayName("비밀번호가 일치하지 않은 경우 실패하고 HTTP 401을 반환한다.")
        @Test
        void Given_NotMatchesPassword_When_Create_Then_HTTP401() {
            // GIVEN
            final var request = UserFixture.SESSION_CREATE_REQUEST;
            willThrow(NotMatchesPasswordException.class).given(sessionFacade).create(request);

            // WHEN
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isUnauthorized(),
                status().reason(NotMatchesPasswordException.REASON));

            then(sessionFacade).should(only()).create(request);
        }

        @SneakyThrows
        @DisplayName("입력 값이 올바른 경우 HTTP 200을 반환한다.")
        @Test
        void Given_CorrectInput_When_Create_Then_HTTP200() {
            // GIVEN
            final var request = UserFixture.SESSION_CREATE_REQUEST;

            // WHEN
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isOk());
            then(sessionFacade).should(only()).create(request);
        }

    }

    @DisplayName("세션 만료 시")
    @Nested
    public class When_Invalidate {

        @SneakyThrows
        @DisplayName("유효하지 않은 세션인 경우 실패하고 HTTP 401을 반환한다.")
        @Test
        void Given_InValidSession_When_Invalidate_Then_HTTP401() {
            // GIVEN
            final var account = UserFixture.ACCOUNT;
            willThrow(InvalidSessionException.class).given(sessionFacade).getAccount();

            // WHEN
            final var when = performDelete();

            // THEN
            when.andExpectAll(status().isUnauthorized());
            then(sessionFacade).should(only()).getAccount();
        }

        @SneakyThrows
        @DisplayName("유효한 세션인 경우 성공하고 HTTP 200을 반환한다.")
        @Test
        void Given_ValidSession_When_Invalidate_Then_HTTP200() {
            // GIVEN - Aspect - Test
            final var principal = UserFixture.PRINCIPAL;
            given(sessionFacade.getAccount()).willReturn(principal);

            // WHEN
            final var when = performDelete();

            // THEN
            when.andExpectAll(status().isOk());
            then(sessionFacade).should().getAccount();
            then(sessionFacade).should().invalidate();
        }

    }

}
