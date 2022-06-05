package me.jaeyeopme.sns.domain.user.api;

import static me.jaeyeopme.sns.domain.user.api.AccountAPI.ACCOUNT_API_V1;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
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
import me.jaeyeopme.sns.domain.fixture.UserFixture;
import me.jaeyeopme.sns.domain.user.application.AccountService;
import me.jaeyeopme.sns.domain.user.application.LoginService;
import me.jaeyeopme.sns.domain.user.exception.DuplicateEmailException;
import me.jaeyeopme.sns.domain.user.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.domain.user.exception.NotFoundEmailException;
import me.jaeyeopme.sns.domain.user.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.domain.user.record.UserLoginRequest;
import org.apache.logging.log4j.util.Strings;
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

@WebMvcTest(AccountAPI.class)
public class AccountAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @MockBean
    private LoginService loginService;

    @SneakyThrows
    private ResultActions performPost(final String urlTemplate, final Object content) {
        return mockMvc.perform(
            post(ACCOUNT_API_V1 + urlTemplate).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(content))).andDo(print());
    }

    private ResultActions performGet(final String urlTemplate, final Object... uriVar)
        throws Exception {
        return mockMvc.perform(
                get(ACCOUNT_API_V1 + urlTemplate, uriVar).characterEncoding(StandardCharsets.UTF_8))
            .andDo(print());
    }

    @DisplayName("로그인을 시")
    @Nested
    public class When_Login {

        @SneakyThrows
        @DisplayName("이메일이 존재하지 않은 경우 실패하고 HTTP 404를 반환한다.")
        @Test
        void Given_NotExistsEmail_When_Login_Then_HTTP404() {
            // GIVEN
            final var request = new UserLoginRequest(UserFixture.EMAIL, UserFixture.RAW_PASSWORD);
            willThrow(NotFoundEmailException.class).given(loginService).login(request);

            // WHEN
            final var when = performPost("/login", request);

            // THEN
            when.andExpectAll(status().isNotFound(),
                status().reason(NotFoundEmailException.REASON));
            then(loginService).should(only()).login(request);
        }

        @SneakyThrows
        @DisplayName("비밀번호가 일치하지 않은 경우 실패하고 HTTP 401을 반환한다.")
        @Test
        void Given_NotMatchesPassword_When_Login_Then_HTTP401() {
            // GIVEN
            final var request = new UserLoginRequest(UserFixture.EMAIL, UserFixture.RAW_PASSWORD);
            willThrow(NotMatchesPasswordException.class).given(loginService).login(request);

            // WHEN
            final var when = performPost("/login", request);

            // THEN
            when.andExpectAll(status().isUnauthorized(),
                status().reason(NotMatchesPasswordException.REASON));
            then(loginService).should(only()).login(request);
        }

        @SneakyThrows
        @DisplayName("입력 값이 올바른 경우 HTTP 200을 반환한다.")
        @Test
        void Given_CorrectInput_When_Login_Then_HTTP200() {
            // GIVEN
            final var request = new UserLoginRequest(UserFixture.EMAIL, UserFixture.RAW_PASSWORD);

            // WHEN
            final var when = performPost("/login", request);

            // THEN
            when.andExpectAll(status().isOk());
            then(loginService).should(only()).login(request);
        }

    }

    @DisplayName("회원 가입 시")
    @Nested
    public class When_Create {

        @SneakyThrows
        @DisplayName("이메일이 중복되는 경우 실패하고 HTTP 409를 반환한다.")
        @Test
        void Given_DuplicatedEmail_When_Creat_Then_HTTP409() {
            // GIVEN
            final var request = UserFixture.USER_CREATE_REQUEST;

            given(accountService.create(request)).willThrow(new DuplicateEmailException());

            // WHEN
            final var when = performPost(Strings.EMPTY, request);

            // THEN
            when.andExpectAll(status().isConflict(),
                status().reason(DuplicateEmailException.REASON));
            then(accountService).should(only()).create(request);
        }

        @SneakyThrows
        @DisplayName("전화번호가 중복되는 경우 실패하고 HTTP 409를 반환한다.")
        @Test
        void Given_DuplicatedPhone_When_Creat_Then_HTTP409() {
            // GIVEN
            final var request = UserFixture.USER_CREATE_REQUEST;
            given(accountService.create(UserFixture.USER_CREATE_REQUEST)).willThrow(
                new DuplicatePhoneException());

            // WHEN
            final var when = performPost(Strings.EMPTY, request);

            // THEN
            when.andExpectAll(status().isConflict(),
                status().reason(DuplicatePhoneException.REASON));
            then(accountService).should(only()).create(request);
        }

        @SneakyThrows
        @DisplayName("입력 값이 올바른 경우 성공하고 HTTP 201을 반환한다.")
        @Test
        void Given_CorrectInput_When_Create_Then_HTTP201() {
            // GIVEN
            final var request = UserFixture.USER_CREATE_REQUEST;

            given(accountService.create(request)).willReturn(1L);

            // WHEN
            final var when = performPost(Strings.EMPTY, request);

            // THEN
            when.andExpectAll(status().isCreated(),
                header().string(HttpHeaders.LOCATION, "%s/%s".formatted(ACCOUNT_API_V1, 1L)));
            then(accountService).should(only()).create(request);
        }

    }

    @DisplayName("이메일 중복 검사 시")
    @Nested
    public class When_VerifyDuplicatedEmail {

        @SneakyThrows
        @DisplayName("중복되는 경우 실패하고 HTTP 409를 반환한다.")
        @Test
        void Given_CorrectInput_When_VerifyDuplicatedEmail_Then_HTTP409() {
            // GIVEN
            final var email = UserFixture.EMAIL;
            willThrow(DuplicateEmailException.class).given(accountService)
                .verifyDuplicatedEmail(email);

            // WHEN
            final var when = performGet("/email/{email}", email.getValue());

            // THEN
            when.andExpectAll(status().isConflict(),
                status().reason(DuplicateEmailException.REASON));
            then(accountService).should(only()).verifyDuplicatedEmail(email);
        }

        @SneakyThrows
        @DisplayName("중복되지 않은 경우 성공하고 HTTP 200을 반환한다.")
        @Test
        void Given_CorrectInput_When_VerifyDuplicatedEmail_Then_HTTP200() {
            // GIVEN
            final var email = UserFixture.EMAIL;
            willDoNothing().given(accountService).verifyDuplicatedEmail(email);

            // WHEN
            final var when = performGet("/email/{email}", email.getValue());

            // THEN
            when.andExpectAll(status().isOk());
            then(accountService).should(only()).verifyDuplicatedEmail(email);
        }

    }

    @DisplayName("전화번호 중복 검사 시")
    @Nested
    public class When_VerifyDuplicatedPhone {

        @SneakyThrows
        @DisplayName("중복되는 경우 실패하고 HTTP 409를 반환한다.")
        @Test
        void Given_CorrectInput_When_ExistsEmail_Then_HTTP409() {
            // GIVEN
            final var phone = UserFixture.PHONE;
            willThrow(DuplicatePhoneException.class).given(accountService)
                .verifyDuplicatedPhone(phone);

            // WHEN
            final var when = performGet("/phone/{phone}", phone.getValue());

            // THEN
            when.andExpectAll(status().isConflict(),
                status().reason(DuplicatePhoneException.REASON));
            then(accountService).should(only()).verifyDuplicatedPhone(phone);
        }

        @SneakyThrows
        @DisplayName("중복되지 않은 경우 성공하고 HTTP 200을 반환한다.")
        @Test
        void Given_CorrectInput_When_VerifyDuplicatedPhone_Then_HTTP200() {
            // GIVEN
            final var phone = UserFixture.PHONE;
            willDoNothing().given(accountService).verifyDuplicatedPhone(phone);

            // WHEN
            final var when = performGet("/phone/{phone}", phone.getValue());

            // THEN
            when.andExpectAll(status().isOk());
            then(accountService).should(only()).verifyDuplicatedPhone(phone);
        }

    }

}
