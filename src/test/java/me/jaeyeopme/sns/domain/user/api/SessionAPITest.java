package me.jaeyeopme.sns.domain.user.api;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.jaeyeopme.sns.domain.fixture.UserFixture;
import me.jaeyeopme.sns.domain.user.application.SessionService;
import me.jaeyeopme.sns.domain.user.exception.InvalidSessionException;
import me.jaeyeopme.sns.domain.user.exception.NotFoundEmailException;
import me.jaeyeopme.sns.domain.user.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.domain.user.record.SessionCreateRequest;
import me.jaeyeopme.sns.global.aop.SessionRequiredAspect;
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
    private SessionService sessionService;

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
            final var request = new SessionCreateRequest(UserFixture.EMAIL,
                UserFixture.RAW_PASSWORD);
            willThrow(NotFoundEmailException.class).given(sessionService).create(request);

            // WHEN
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isNotFound(),
                status().reason(NotFoundEmailException.REASON));
            then(sessionService).should(only()).create(request);
        }

        @SneakyThrows
        @DisplayName("비밀번호가 일치하지 않은 경우 실패하고 HTTP 401을 반환한다.")
        @Test
        void Given_NotMatchesPassword_When_Create_Then_HTTP401() {
            // GIVEN
            final var request = new SessionCreateRequest(UserFixture.EMAIL,
                UserFixture.RAW_PASSWORD);
            willThrow(NotMatchesPasswordException.class).given(sessionService).create(request);

            // WHEN
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isUnauthorized(),
                status().reason(NotMatchesPasswordException.REASON));
            then(sessionService).should(only()).create(request);
        }

        @SneakyThrows
        @DisplayName("입력 값이 올바른 경우 HTTP 200을 반환한다.")
        @Test
        void Given_CorrectInput_When_Create_Then_HTTP200() {
            // GIVEN
            final var request = new SessionCreateRequest(UserFixture.EMAIL,
                UserFixture.RAW_PASSWORD);

            // WHEN
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isOk());
            then(sessionService).should(only()).create(request);
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
            willThrow(InvalidSessionException.class).given(sessionService).getInfo();

            // WHEN
            final var when = performDelete();

            // THEN
            when.andExpectAll(status().isUnauthorized());
            then(sessionService).should(only()).getInfo();
        }


        @SneakyThrows
        @DisplayName("유효한 세션인 경우 성공하고 HTTP 200을 반환한다.")
        @Test
        void Given_ValidSession_When_Invalidate_Then_HTTP200() {
            // GIVEN - Aspect - Test
            final var account = UserFixture.ACCOUNT;
            given(sessionService.getInfo()).willReturn(account);

            // WHEN
            final var when = performDelete();

            // THEN
            when.andExpectAll(status().isOk());
            then(sessionService).should().getInfo();
            then(sessionService).should().invalidate();
        }

    }

}
