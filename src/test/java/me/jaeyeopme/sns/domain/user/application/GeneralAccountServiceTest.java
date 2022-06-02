package me.jaeyeopme.sns.domain.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;

import me.jaeyeopme.sns.domain.user.domain.Account;
import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.EncodedPassword;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;
import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.exception.DuplicateEmailException;
import me.jaeyeopme.sns.domain.user.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.domain.user.record.AccountRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class GeneralAccountServiceTest {

    private final static Email EMAIL = Email.of("email@email.com");

    private final static Phone PHONE = Phone.of("+821012345678");

    private final static RawPassword RAW_PASSWORD = RawPassword.of("password1234");

    private final static String NAME = "name";

    private final static String BIO = "bio";

    @InjectMocks
    private GeneralAccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountPasswordEncoder encoder;

    @DisplayName("이메일이 중복되는 경우 회원 가입을 실패 한다.")
    @Test
    void Given_DuplicateEmail_When_Create_Then_ThrowException() {
        // GIVEN
        final var request = new AccountRequest(EMAIL.getValue(),
            PHONE.getValue(),
            RAW_PASSWORD.getValue(),
            NAME, BIO);

        given(userRepository.existsByAccountEmailValue(request.email().getValue()))
            .willReturn(Boolean.TRUE);

        // WHEN
        final Executable when = () -> accountService.create(request);

        // THEN
        assertThrows(DuplicateEmailException.class, when, DuplicateEmailException.REASON);
        then(userRepository).should().existsByAccountEmailValue(request.email().getValue());
        then(userRepository).should(never()).existsByAccountPhoneValue(request.phone().getValue());
        then(userRepository).should(never()).save(any(User.class));
    }

    @DisplayName("전화번호가 중복되는 경우 회원 가입을 실패 한다.")
    @Test
    void Given_DuplicatePhone_When_Create_Then_ThrowException() {
        // GIVEN
        final var request = new AccountRequest(EMAIL.getValue(),
            PHONE.getValue(),
            RAW_PASSWORD.getValue(),
            NAME, BIO);

        given(userRepository.existsByAccountEmailValue(request.email().getValue()))
            .willReturn(Boolean.FALSE);
        given(userRepository.existsByAccountPhoneValue(request.phone().getValue()))
            .willReturn(Boolean.TRUE);

        // WHEN
        final Executable when = () -> accountService.create(request);

        // THEN
        assertThrows(DuplicatePhoneException.class, when, DuplicatePhoneException.REASON);
        then(userRepository).should().existsByAccountEmailValue(request.email().getValue());
        then(userRepository).should().existsByAccountPhoneValue(request.phone().getValue());
        then(userRepository).should(never()).save(any(User.class));
    }

    @DisplayName("회원을 저장하고 반환한다.")
    @Test
    void Given_CorrectInput_When_Create_Then_User() {
        // GIVEN
        final var request = new AccountRequest(EMAIL.getValue(),
            PHONE.getValue(),
            RAW_PASSWORD.getValue(),
            NAME, BIO);

        final var encodedPassword = EncodedPassword.of(RAW_PASSWORD.getValue().toString());
        given(encoder.encode(RAW_PASSWORD))
            .willReturn(encodedPassword);

        final var expected = User.of(Account.of(request, encodedPassword));
        ReflectionTestUtils.setField(expected, "id", 1L);
        given(userRepository.save(any(User.class))).willReturn(expected);

        given(userRepository.existsByAccountEmailValue(request.email().getValue()))
            .willReturn(Boolean.FALSE);
        given(userRepository.existsByAccountPhoneValue(request.phone().getValue()))
            .willReturn(Boolean.FALSE);

        // WHEN
        final var actual = accountService.create(request);

        // THEN
        assertThat(actual).isEqualTo(expected.getId());
        then(userRepository).should()
            .existsByAccountEmailValue(request.email().getValue());
        then(userRepository).should()
            .existsByAccountPhoneValue(request.phone().getValue());
        then(userRepository).should()
            .save(any(User.class));
    }

    @DisplayName("이메일이 중복되는 경우 중복검사에 실패한다.")
    @Test
    void Given_DuplicatedEmail_When_VerifyDuplicatedEmail_Then_ThrowException() {
        // GIVEN
        final var email = EMAIL;
        given(userRepository.existsByAccountEmailValue(email.getValue())).willReturn(Boolean.TRUE);

        // WHEN
        final Executable when = () -> accountService.verifyDuplicatedEmail(email);

        // THEN
        assertThrows(DuplicateEmailException.class, when);
        then(userRepository).should(only())
            .existsByAccountEmailValue(email.getValue());
    }

    @DisplayName("이메일이 중복되지 않은 경우 중복검사에 성공한다.")
    @Test
    void Given_NotDuplicatedEmail_When_VerifyDuplicatedEmail_Then_DoNothing() {
        // GIVEN
        final var email = EMAIL;
        given(userRepository.existsByAccountEmailValue(email.getValue()))
            .willReturn(Boolean.FALSE);

        // WHEN
        accountService.verifyDuplicatedEmail(email);

        // THEN
        then(userRepository).should(only())
            .existsByAccountEmailValue(email.getValue());
    }

    @DisplayName("전화번호가 중복되는 경우 중복검사에 실패한다.")
    @Test
    void Given_DuplicatedPhone_When_VerifyDuplicatedPhone_Then_ThrowException() {
        // GIVEN
        final var phone = PHONE;
        given(userRepository.existsByAccountPhoneValue(phone.getValue()))
            .willReturn(Boolean.TRUE);

        // WHEN
        final Executable when = () -> accountService.verifyDuplicatedPhone(phone);

        // THEN
        assertThrows(DuplicatePhoneException.class, when);
        then(userRepository).should(only())
            .existsByAccountPhoneValue(phone.getValue());
    }

    @DisplayName("전화번호가 중복되지 않은 경우 중복검사에 성공한다.")
    @Test
    void Given_NotDuplicatedPhone_When_VerifyDuplicatedPhone_Then_DoNothing() {
        // GIVEN
        final var phone = PHONE;
        given(userRepository.existsByAccountPhoneValue(phone.getValue()))
            .willReturn(Boolean.FALSE);

        // WHEN
        accountService.verifyDuplicatedPhone(phone);

        // THEN
        then(userRepository).should(only())
            .existsByAccountPhoneValue(phone.getValue());
    }

}