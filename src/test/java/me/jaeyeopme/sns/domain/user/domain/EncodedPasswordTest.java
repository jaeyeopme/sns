package me.jaeyeopme.sns.domain.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EncodedPasswordTest {

    @DisplayName("비밀번호를 생성한다.")
    @Test
    void create() {
        // GIVEN
        final var value = "password1234";

        // WHEN
        final var encodedPassword = EncodedPassword.of(value);

        // THEN
        assertThat(value).isEqualTo(encodedPassword.getValue());
    }

}