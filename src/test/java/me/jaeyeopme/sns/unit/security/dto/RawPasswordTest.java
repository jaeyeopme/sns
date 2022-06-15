package me.jaeyeopme.sns.unit.security.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.common.security.dto.RawPassword;
import me.jaeyeopme.sns.support.ConstraintViolationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class RawPasswordTest extends ConstraintViolationTest {

    @DisplayName("입력 값이 최소 8자, 하나 이상의 문자와 숫자인 경우 비밀번호를 생성한다.")
    @Test
    void create() {
        // GIVEN
        final var value = "password1234";
        final var password = RawPassword.of(value);

        // WHEN
        final var when = validate(password);

        // THEN
        assertThat(when.isEmpty()).isTrue();
        assertThat(value).isEqualTo(password.value());
    }

    @DisplayName("입력 값이 비어있을 경우 비밀번호 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void Given_NullOrBlank_When_Create_Then_ThrowException(final String value) {
        // GIVEN
        final var password = RawPassword.of(value);

        // WHEN
        final var when = validate(password);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

    @DisplayName("잘못된 형식의 입력 값인 경우 비밀번호 생성을 실패한다.")
    @CsvSource({"pass123", "password", "1234", "@asdf", "@1234"})
    @ParameterizedTest
    void Given_InvalidFormed_When_Create_Then_ThrowException(final String value) {
        // GIVEN
        final var password = RawPassword.of(value);

        // WHEN
        final var when = validate(password);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

}