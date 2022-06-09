package me.jaeyeopme.sns.domain.user.api;

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
import me.jaeyeopme.sns.domain.fixture.UserFixture;
import me.jaeyeopme.sns.domain.user.application.AccountService;
import me.jaeyeopme.sns.domain.user.exception.DuplicateEmailException;
import me.jaeyeopme.sns.domain.user.exception.DuplicatePhoneException;
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

    @SneakyThrows
    private ResultActions performPost(final Object content) {
        return mockMvc.perform(
                post(AccountAPI.V1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(content)))
            .andDo(print());
    }

    @SneakyThrows
    private ResultActions performGet(final String urlTemplate,
        final Object... uriVar) {
        return mockMvc.perform(
                get(AccountAPI.V1 + urlTemplate, uriVar)
                    .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print());
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
            final var when = performPost(request);

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
            final var when = performPost(request);

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
            final var when = performPost(request);

            // THEN
            when.andExpectAll(status().isCreated(),
                header().string(HttpHeaders.LOCATION, "%s/%s".formatted(AccountAPI.V1, 1L)));
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

            // WHEN
            final var when = performGet("/phone/{phone}", phone.getValue());

            // THEN
            when.andExpectAll(status().isOk());
            then(accountService).should(only()).verifyDuplicatedPhone(phone);
        }

    }

}
