package me.jaeyeopme.sns.unit.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.user.domain.EncodedPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EncodedPasswordTest {

    @DisplayName("입력 값이 올바른 경우 생성을 성공한다.")
    @Test
    void 입력_값이_올바른_경우_생성_성공() {
        // GIVEN
        final var value = "password1234";

        // WHEN
        final var encodedPassword = EncodedPassword.of(value);

        // THEN
        assertThat(value).isEqualTo(encodedPassword.value());
    }

}