package me.jaeyeopme.sns.unit.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import java.util.Optional;
import me.jaeyeopme.sns.common.exception.DuplicateEmailException;
import me.jaeyeopme.sns.common.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.common.exception.NotFoundEmailException;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import me.jaeyeopme.sns.user.application.service.GeneralUserService;
import me.jaeyeopme.sns.user.domain.User;
import me.jaeyeopme.sns.user.domain.repository.UserRepository;
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
class GeneralUserServiceTest {

    @InjectMocks
    private GeneralUserService userService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("회원 저장 시")
    @Nested
    public class When_Save {

        @DisplayName("회원을 저장하고 ID 를 반환한다.")
        @Test
        void Then_SaveAndReturnId() {
            // GIVEN
            final var account = UserFixture.ACCOUNT;
            final var user = User.of(account);
            final Long expected = 1L;
            ReflectionTestUtils.setField(user, "id", expected);
            given(userRepository.create(any(User.class)))
                .willReturn(user);

            // WHEN
            final var actual = userService.create(account);

            // THEN
            assertThat(actual).isEqualTo(expected);
            then(userRepository).should(only()).create(any(User.class));
        }

    }

    @DisplayName("이메일 중복 검사 시")
    @Nested
    public class When_VerifyDuplicatedEmail {

        @DisplayName("중복되는 경우 실패한다.")
        @Test
        void Given_DuplicatedEmail_Then_ThrowException() {
            // GIVEN
            final var email = UserFixture.EMAIL;
            given(userRepository.existsByEmail(email))
                .willReturn(Boolean.TRUE);

            // WHEN
            final Executable when = () -> userService.verifyDuplicatedEmail(email);

            // THEN
            assertThrows(DuplicateEmailException.class, when);
            then(userRepository).should(only()).existsByEmail(email);
        }

        @DisplayName("중복되지 않은 경우 성공한다.")
        @Test
        void Given_NotDuplicatedEmail_Then_DoNothing() {
            // GIVEN
            final var email = UserFixture.EMAIL;
            given(userRepository.existsByEmail(email))
                .willReturn(Boolean.FALSE);

            // WHEN
            userService.verifyDuplicatedEmail(email);

            // THEN
            then(userRepository).should(only()).existsByEmail(email);
        }

    }

    @DisplayName("이메일 조회 시")
    @Nested
    public class When_findByEmail {

        @DisplayName("이메일이 존재하지 않은 경우 실패한다.")
        @Test
        void Given_NotExistsEmail_Then_ThrowException() {
            // GIVEN
            final var email = UserFixture.EMAIL;
            given(userRepository.findByEmail(email)).willReturn(Optional.empty());

            // WHEN
            final Executable when = () -> userService.findByEmail(email);

            // THEN
            assertThrows(NotFoundEmailException.class, when);
            then(userRepository).should(only()).findByEmail(email);
        }

        @DisplayName("이메일이 존재하는 경우 성공한다.")
        @Test
        void Given_ExistsEmail_Then_ReturnUser() {
            // GIVEN
            final var expected = UserFixture.USER;
            given(userRepository.findByEmail(expected.account().email()))
                .willReturn(Optional.of(expected));

            // WHEN
            final var actual = userService.findByEmail(expected.account().email());

            assertThat(actual).isEqualTo(expected);
            then(userRepository).should(only()).findByEmail(expected.account().email());
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
            given(userRepository.existsByPhone(phone))
                .willReturn(Boolean.TRUE);

            // WHEN
            final Executable when = () -> userService.verifyDuplicatedPhone(phone);

            // THEN
            assertThrows(DuplicatePhoneException.class, when);
            then(userRepository).should(only()).existsByPhone(phone);
        }

        @DisplayName("중복되지 않은 경우 성공한다.")
        @Test
        void Given_NotDuplicatedPhone_When_VerifyDuplicatedPhone_Then_DoNothing() {
            // GIVEN
            final var phone = UserFixture.PHONE;
            given(userRepository.existsByPhone(phone))
                .willReturn(Boolean.FALSE);

            // WHEN
            userService.verifyDuplicatedPhone(phone);

            // THEN
            then(userRepository).should(only()).existsByPhone(phone);
        }

    }

}