package me.jaeyeopme.sns.domain.user.api;

import static me.jaeyeopme.sns.domain.user.api.AccountAPI.ACCOUNT_API_V1;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jaeyeopme.sns.domain.user.application.AccountService;
import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.domain.embeded.Account;
import me.jaeyeopme.sns.domain.user.domain.embeded.Email;
import me.jaeyeopme.sns.domain.user.domain.embeded.Password;
import me.jaeyeopme.sns.domain.user.domain.embeded.Phone;
import me.jaeyeopme.sns.domain.user.record.AccountRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AccountAPI.class)
public class AccountAPITest {

    private final static Email EMAIL = Email.of("email@email.com");

    private final static Phone PHONE = Phone.of("+82-10-1234-5678");

    private final static Password PASSWORD = Password.of("password1234");

    private final static String NAME = "name";

    private final static String BIO = "bio";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @DisplayName("입력 값이 올바른 경우 회원을 가입하고 HTTP 201을 반환한다.")
    @Test
    void Given_CorrectInput_When_Create_Then_HTTP201() throws Exception {
        // GIVEN
        final var request = new AccountRequest(EMAIL.getValue(),
            PHONE.getValue(),
            PASSWORD.getValue(),
            NAME, BIO);
        final var user = User.of(Account.of(request));
        final var id = 1L;
        ReflectionTestUtils.setField(user, "id", id);
        given(accountService.create(request)).willReturn(user);

        // WHEN
        final var when = getPostResult(objectMapper.writeValueAsString(request));

        // THEN
        when.andExpectAll(status().isCreated(),
            header().string(HttpHeaders.LOCATION, "%s/%s".formatted(ACCOUNT_API_V1, id)));
    }

    private ResultActions getPostResult(final String content) throws Exception {
        return mockMvc.perform(post(ACCOUNT_API_V1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andDo(print());
    }

}
