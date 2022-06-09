package me.jaeyeopme.sns.domain.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;

import me.jaeyeopme.sns.domain.fixture.UserFixture;
import me.jaeyeopme.sns.domain.user.application.impl.GeneralAccountService;
import me.jaeyeopme.sns.domain.user.domain.Account;
import me.jaeyeopme.sns.domain.user.domain.EncodedPassword;
import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.exception.DuplicateEmailException;
import me.jaeyeopme.sns.domain.user.exception.DuplicatePhoneException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class GeneralAccountServiceTest {

    @InjectMocks
    private GeneralAccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @DisplayName("회원 저장 시")
    @Nested
    public class When_Save {

        @DisplayName("이메일이 중복되는 경우 실패 한다.")
        @Test
        void Given_DuplicateEmail_When_Create_Then_ThrowException() {
            // GIVEN
            final var request = UserFixture.USER_CREATE_REQUEST;

            given(userRepository.existsByAccountEmailValue(request.email().getValue()))
                .willReturn(Boolean.TRUE);

            // WHEN
            final Executable when = () -> accountService.create(request);

            // THEN
            assertThrows(DuplicateEmailException.class, when, DuplicateEmailException.REASON);
            then(userRepository).should().existsByAccountEmailValue(request.email().getValue());
            then(userRepository).should(never())
                .existsByAccountPhoneValue(request.phone().getValue());
            then(userRepository).should(never()).save(any(User.class));
        }

        @DisplayName("전화번호가 중복되는 경우 실패 한다.")
        @Test
        void Given_DuplicatePhone_When_Create_Then_ThrowException() {
            // GIVEN
            final var request = UserFixture.USER_CREATE_REQUEST;

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

        @DisplayName("입력 값이 올바른 경우 저장하고 반환한다.")
        @Test
        void Given_CorrectInput_When_Create_Then_SaveAndReturnUser() {
            // GIVEN
            final var request = UserFixture.USER_CREATE_REQUEST;

            final var encodedPassword = EncodedPassword.of(
                request.password().getValue().toString());
            given(passwordEncryptor.encode(request.password()))
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

    }

    @DisplayName("이메일 중복 검사 시")
    @Nested
    public class When_VerifyDuplicatedEmail {

        @DisplayName("중복되는 경우 실패한다.")
        @Test
        void Given_DuplicatedEmail_When_VerifyDuplicatedEmail_Then_ThrowException() {
            // GIVEN
            final var email = UserFixture.EMAIL;
            given(userRepository.existsByAccountEmailValue(email.getValue())).willReturn(
                Boolean.TRUE);

            // WHEN
            final Executable when = () -> accountService.verifyDuplicatedEmail(email);

            // THEN
            assertThrows(DuplicateEmailException.class, when);
            then(userRepository).should(only())
                .existsByAccountEmailValue(email.getValue());
        }

        @DisplayName("중복되지 않은 경우 성공한다.")
        @Test
        void Given_NotDuplicatedEmail_When_VerifyDuplicatedEmail_Then_DoNothing() {
            // GIVEN
            final var email = UserFixture.EMAIL;
            given(userRepository.existsByAccountEmailValue(email.getValue()))
                .willReturn(Boolean.FALSE);

            // WHEN
            accountService.verifyDuplicatedEmail(email);

            // THEN
            then(userRepository).should(only())
                .existsByAccountEmailValue(email.getValue());
        }

    }

    @DisplayName("전화번호 중복 검사 시")
    @Nested
    public class When_VerifyDuplicatedPhone {

        @DisplayName("중복되는 경우 실패한다.")
        @Test
        void Given_DuplicatedPhone_When_VerifyDuplicatedPhone_Then_ThrowException() {
            // GIVEN
            final var phone = UserFixture.PHONE;
            given(userRepository.existsByAccountPhoneValue(phone.getValue()))
                .willReturn(Boolean.TRUE);

            // WHEN
            final Executable when = () -> accountService.verifyDuplicatedPhone(phone);

            // THEN
            assertThrows(DuplicatePhoneException.class, when);
            then(userRepository).should(only())
                .existsByAccountPhoneValue(phone.getValue());
        }

        @DisplayName("중복되지 않은 경우 성공한다.")
        @Test
        void Given_NotDuplicatedPhone_When_VerifyDuplicatedPhone_Then_DoNothing() {
            // GIVEN
            final var phone = UserFixture.PHONE;
            given(userRepository.existsByAccountPhoneValue(phone.getValue()))
                .willReturn(Boolean.FALSE);

            // WHEN
            accountService.verifyDuplicatedPhone(phone);

            // THEN
            then(userRepository).should(only())
                .existsByAccountPhoneValue(phone.getValue());
        }

    }

}