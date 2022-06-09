package me.jaeyeopme.sns.unit.user.common.security;

import static org.junit.jupiter.api.Assertions.assertThrows;

import me.jaeyeopme.sns.common.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.common.security.BCryptPasswordEncryptor;
import me.jaeyeopme.sns.common.security.PasswordEncryptor;
import me.jaeyeopme.sns.common.security.dto.RawPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class BCryptPasswordEncryptorTest {

    private PasswordEncryptor encryptor;

    @BeforeEach
    void setUp() {
        this.encryptor = new BCryptPasswordEncryptor();
    }

    @DisplayName("비밀번호 인코딩을 성공 합니다.")
    @Test
    void encode() {
        // GIVEN
        final var rawPassword = RawPassword.of("password1234");

        // WHEN
        final var encodedPassword = encryptor.encode(rawPassword);

        // THEN
        encryptor.matches(rawPassword, encodedPassword);
    }

    @DisplayName("인코딩 되지 않은 비밀번호와 인코딩 된 비밀번호의 일치 여부를 판단합니다.")
    @Test
    void matches() {
        // GIVEN
        final var rawPassword1 = RawPassword.of("password1234");
        final var rawPassword2 = RawPassword.of("1234password");
        final var encodedPassword1 = encryptor.encode(rawPassword1);
        final var encodedPassword2 = encryptor.encode(rawPassword2);

        // WHEN
        final Executable when1 = () -> encryptor.matches(rawPassword1, encodedPassword2);
        final Executable when2 = () -> encryptor.matches(rawPassword2, encodedPassword1);

        // THEN
        assertThrows(NotMatchesPasswordException.class, when1);
        assertThrows(NotMatchesPasswordException.class, when2);
    }

}