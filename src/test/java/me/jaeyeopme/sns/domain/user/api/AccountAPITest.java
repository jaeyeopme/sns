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
import me.jaeyeopme.sns.domain.user.application.AccountService;
import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.Name;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;
import me.jaeyeopme.sns.domain.user.exception.DuplicateEmailException;
import me.jaeyeopme.sns.domain.user.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.domain.user.record.AccountRequest;
import org.junit.jupiter.api.DisplayName;
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

    private final static Email EMAIL = Email.of("email@email.com");

    private final static Phone PHONE = Phone.of("+821012345678");

    private final static RawPassword RAW_PASSWORD = RawPassword.of("password1234");

    private final static Name NAME = Name.of("name");

    private final static String BIO = "bio";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @DisplayName("이메일이 중복되는 경우 회원 가입을 실패하고 HTTP 409를 반환한다.")
    @Test
    void Given_DuplicatedEmail_When_Creat_Then_HTTP409() throws Exception {
        // GIVEN
        final var request = new AccountRequest(
            EMAIL.getValue(),
            PHONE.getValue(),
            RAW_PASSWORD.getValue(),
            NAME.getValue(),
            BIO);
        given(accountService.create(request))
            .willThrow(new DuplicateEmailException());

        // WHEN
        final var when = getPostResult(objectMapper.writeValueAsString(request));

        // THEN
        when.andExpectAll(status().isConflict(),
            status().reason(DuplicateEmailException.REASON));
        then(accountService).should(only())
            .create(request);
    }

    @DisplayName("전화번호가 중복되는 경우 회원 가입을 실패하고 HTTP 409를 반환한다.")
    @Test
    void Given_DuplicatedPhone_When_Creat_Then_HTTP409() throws Exception {
        // GIVEN
        final var request = new AccountRequest(
            EMAIL.getValue(),
            PHONE.getValue(),
            RAW_PASSWORD.getValue(),
            NAME.getValue(),
            BIO);
        given(accountService.create(request))
            .willThrow(new DuplicatePhoneException());

        // WHEN
        final var when = getPostResult(objectMapper.writeValueAsString(request));

        // THEN
        when.andExpectAll(status().isConflict(),
            status().reason(DuplicatePhoneException.REASON));
        then(accountService).should(only())
            .create(request);
    }

    @DisplayName("입력 값이 올바른 경우 회원을 가입하고 HTTP 201을 반환한다.")
    @Test
    void Given_CorrectInput_When_Create_Then_HTTP201() throws Exception {
        // GIVEN
        final var request = new AccountRequest(
            EMAIL.getValue(),
            PHONE.getValue(),
            RAW_PASSWORD.getValue(),
            NAME.getValue(),
            BIO);
        given(accountService.create(request))
            .willReturn(1L);

        // WHEN
        final var when = getPostResult(objectMapper.writeValueAsString(request));

        // THEN
        when.andExpectAll(status().isCreated(),
            header().string(HttpHeaders.LOCATION, "%s/%s".formatted(ACCOUNT_API_V1, 1L)));
        then(accountService).should(only())
            .create(request);
    }

    @DisplayName("이메일이 중복되지 않은 경우 HTTP 200을 반환한다.")
    @Test
    void Given_CorrectInput_When_VerifyDuplicatedEmail_Then_HTTP200() throws Exception {
        // GIVEN
        willDoNothing().given(accountService)
            .verifyDuplicatedEmail(EMAIL);

        // WHEN
        final var when = getGetResult("/email/{email}", EMAIL.getValue());

        // THEN
        when.andExpectAll(status().isOk());
        then(accountService).should(only())
            .verifyDuplicatedEmail(EMAIL);
    }

    @DisplayName("이메일이 중복되는 경우 HTTP 409를 반환한다.")
    @Test
    void Given_CorrectInput_When_VerifyDuplicatedEmail_Then_HTTP409() throws Exception {
        // GIVEN
        willThrow(DuplicateEmailException.class).given(accountService)
            .verifyDuplicatedEmail(EMAIL);

        // WHEN
        final var when = getGetResult("/email/{email}", EMAIL.getValue());

        // THEN
        when.andExpectAll(status().isConflict(),
            status().reason(DuplicateEmailException.REASON));
        then(accountService).should(only())
            .verifyDuplicatedEmail(EMAIL);
    }

    @DisplayName("전화번호가 중복되지 않은 경우 HTTP 200을 반환한다.")
    @Test
    void Given_CorrectInput_When_VerifyDuplicatedPhone_Then_HTTP200() throws Exception {
        // GIVEN
        willDoNothing().given(accountService)
            .verifyDuplicatedPhone(PHONE);

        // WHEN
        final var when = getGetResult("/phone/{phone}", PHONE.getValue());

        // THEN
        when.andExpectAll(status().isOk());
        then(accountService).should(only())
            .verifyDuplicatedPhone(PHONE);
    }

    @DisplayName("전화번호가 중복되는 경우 HTTP 409를 반환한다.")
    @Test
    void Given_CorrectInput_When_ExistsEmail_Then_HTTP409() throws Exception {
        // GIVEN
        willThrow(DuplicatePhoneException.class).given(accountService)
            .verifyDuplicatedPhone(PHONE);

        // WHEN
        final var when = getGetResult("/phone/{phone}", PHONE.getValue());

        // THEN
        when.andExpectAll(status().isConflict(),
            status().reason(DuplicatePhoneException.REASON));
        then(accountService).should(only())
            .verifyDuplicatedPhone(PHONE);
    }

    private ResultActions getPostResult(final String content) throws Exception {
        return mockMvc.perform(post(ACCOUNT_API_V1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andDo(print());
    }

    private ResultActions getGetResult(final String template,
        final String value) throws Exception {
        return mockMvc.perform(get(ACCOUNT_API_V1 + template, value)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print());
    }

}
