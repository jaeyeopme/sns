package me.jaeyeopme.sns.domain.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.domain.embeded.Account;
import me.jaeyeopme.sns.domain.user.domain.embeded.Email;
import me.jaeyeopme.sns.domain.user.domain.embeded.Password;
import me.jaeyeopme.sns.domain.user.domain.embeded.Phone;
import me.jaeyeopme.sns.domain.user.record.AccountRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneralAccountServiceTest {

    private final static Email EMAIL = Email.of("email@email.com");

    private final static Phone PHONE = Phone.of("+82-10-1234-5678");

    private final static Password PASSWORD = Password.of("password1234");

    private final static String NAME = "name";

    private final static String BIO = "bio";

    @InjectMocks
    private GeneralAccountService accountService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("회원을 저장하고 반환한다.")
    @Test
    void Given_CorrectInput_When_Create_Then_User() {
        // GIVEN
        final var request = new AccountRequest(EMAIL.getValue(),
            PHONE.getValue(),
            PASSWORD.getValue(),
            NAME, BIO);
        final var expected = User.of(Account.of(request));
        given(userRepository.save(any(User.class))).willReturn(expected);

        // WHEN
        final User actual = accountService.create(request);

        // THEN
        assertThat(actual).isEqualTo(expected);
        then(userRepository).should().save(any(User.class));
    }

}