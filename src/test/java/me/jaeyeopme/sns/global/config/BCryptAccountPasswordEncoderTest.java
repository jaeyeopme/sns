package me.jaeyeopme.sns.global.config;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.domain.user.application.AccountPasswordEncoder;
import me.jaeyeopme.sns.domain.user.application.BCryptAccountPasswordEncoder;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BCryptAccountPasswordEncoderTest {

    private AccountPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        this.encoder = new BCryptAccountPasswordEncoder();
    }

    @DisplayName("비밀번호 인코딩을 성공 합니다.")
    @Test
    void encode() {
        // GIVEN
        final var rawPassword = RawPassword.of("password1234");
        final var encodedPassword = encoder.encode(rawPassword);

        // WHEN
        final boolean actual = encoder.matches(rawPassword, encodedPassword);

        // THEN
        assertThat(actual).isTrue();
    }

    @DisplayName("인코딩 되지 않은 비밀번호와 인코딩 된 비밀번호의 일치 여부를 판단합니다.")
    @Test
    void matches() {
        // GIVEN
        final var rawPassword1 = RawPassword.of("password1234");
        final var rawPassword2 = RawPassword.of("1234password");
        final var encodedPassword1 = encoder.encode(rawPassword1);
        final var encodedPassword2 = encoder.encode(rawPassword2);

        // WHEN
        final boolean actual1 = encoder.matches(rawPassword1, encodedPassword1);
        final boolean actual2 = encoder.matches(rawPassword2, encodedPassword2);
        final boolean actual3 = encoder.matches(rawPassword1, encodedPassword2);
        final boolean actual4 = encoder.matches(rawPassword2, encodedPassword1);

        // THEN
        assertThat(actual1).isTrue();
        assertThat(actual2).isTrue();
        assertThat(actual3).isFalse();
        assertThat(actual4).isFalse();
    }

}