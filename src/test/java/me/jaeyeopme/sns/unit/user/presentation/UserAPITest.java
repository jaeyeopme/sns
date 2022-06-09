package me.jaeyeopme.sns.unit.user.presentation;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import me.jaeyeopme.sns.common.exception.DuplicateEmailException;
import me.jaeyeopme.sns.common.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.support.user.fixture.UserFixture;
import me.jaeyeopme.sns.user.application.UserFacade;
import me.jaeyeopme.sns.user.presentation.UserAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(UserAPI.class)
public class UserAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserFacade userFacade;

    @SneakyThrows
    private ResultActions performPost(final Object content) {
        return mockMvc.perform(
                post(UserAPI.V1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(content)))
            .andDo(print());
    }

    @SneakyThrows
    private ResultActions performGet(final String urlTemplate,
        final Object... uriVar) {
        return mockMvc.perform(
                get(UserAPI.V1 + urlTemplate, uriVar)
                    .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print());
    }

    @DisplayName("회원 가입 시")
    @Nested
    public class When_Create {

        @SneakyThrows
        @DisplayName("이메일이 중복되는 경우 실패하고 HTTP 409를 반환한다.")
        @Test
        void Given_DuplicatedEmail_Then_HTTP409() {
            // GIVEN
            final var request = UserFixture.USER_CREATE_REQUEST;
            willThrow(new DuplicateEmailException()).given(userFacade).create(request);

            // WHEN
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isConflict(),
                status().reason(DuplicateEmailException.REASON));
            then(userFacade).should(only()).create(request);
        }

        @SneakyThrows
        @DisplayName("전화번호가 중복되는 경우 실패하고 HTTP 409를 반환한다.")
        @Test
        void Given_DuplicatedPhone_Then_HTTP409() {
            // GIVEN
            final var request = UserFixture.USER_CREATE_REQUEST;
            willThrow(new DuplicatePhoneException()).given(userFacade).create(request);

            // WHEN
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isConflict(),
                status().reason(DuplicatePhoneException.REASON));
            then(userFacade).should(only()).create(request);
        }

        @SneakyThrows
        @DisplayName("입력 값이 올바른 경우 성공하고 HTTP 201을 반환한다.")
        @Test
        void Given_CorrectInput_Then_HTTP201() {
            // GIVEN
            final var request = UserFixture.USER_CREATE_REQUEST;
            given(userFacade.create(request)).willReturn(1L);

            // WHEN
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isCreated(),
                header().string(HttpHeaders.LOCATION, "%s/%s".formatted(UserAPI.V1, 1L)));
            then(userFacade).should(only()).create(request);
        }

    }

    @DisplayName("이메일 중복 검사 시")
    @Nested
    public class When_VerifyDuplicatedEmail {

        @SneakyThrows
        @DisplayName("중복되는 경우 실패하고 HTTP 409를 반환한다.")
        @Test
        void Given_DuplicatedEmail_Then_HTTP409() {
            // GIVEN
            final var request = UserFixture.EMAIL_REQUEST;
            willThrow(DuplicateEmailException.class).given(userFacade)
                .verifyDuplicatedEmail(request);

            // WHEN
            final var when = performGet("/email/{email}", request.email());

            // THEN
            when.andExpectAll(status().isConflict(),
                status().reason(DuplicateEmailException.REASON));
            then(userFacade).should(only())
                .verifyDuplicatedEmail(request);
        }

        @SneakyThrows
        @DisplayName("중복되지 않은 경우 성공하고 HTTP 200을 반환한다.")
        @Test
        void Given_NotDuplicatedEmail_Then_HTTP200() {
            // GIVEN
            final var request = UserFixture.EMAIL_REQUEST;

            // WHEN
            final var when = performGet("/email/{email}", request.email());

            // THEN
            when.andExpectAll(status().isOk());
            then(userFacade).should(only()).verifyDuplicatedEmail(request);
        }

    }

    @DisplayName("전화번호 중복 검사 시")
    @Nested
    public class When_VerifyDuplicatedPhone {

        @SneakyThrows
        @DisplayName("중복되는 경우 실패하고 HTTP 409를 반환한다.")
        @Test
        void Given_DuplicatedPhone_Then_HTTP409() {
            // GIVEN
            final var request = UserFixture.PHONE_REQUEST;
            willThrow(DuplicatePhoneException.class).given(userFacade)
                .verifyDuplicatedPhone(request);

            // WHEN
            final var when = performGet("/phone/{phone}", request.phone());

            // THEN
            when.andExpectAll(status().isConflict(),
                status().reason(DuplicatePhoneException.REASON));
            then(userFacade).should(only()).verifyDuplicatedPhone(request);
        }

        @SneakyThrows
        @DisplayName("중복되지 않은 경우 성공하고 HTTP 200을 반환한다.")
        @Test
        void Given_NotDuplicatedPhone_Then_HTTP200() {
            // GIVEN
            final var request = UserFixture.PHONE_REQUEST;

            // WHEN
            final var when = performGet("/phone/{phone}", request.phone());

            // THEN
            when.andExpectAll(status().isOk());
            then(userFacade).should(only()).verifyDuplicatedPhone(request);
        }

    }

}
