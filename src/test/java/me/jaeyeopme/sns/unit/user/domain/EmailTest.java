package me.jaeyeopme.sns.unit.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import me.jaeyeopme.sns.common.exception.InvalidArgumentException;
import me.jaeyeopme.sns.user.domain.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class EmailTest {

    @DisplayName("입력 값이 올바른 형식인 경우 이메일을 생성한다.")
    @Test
    void create() {
        // GIVEN
        final var value = "email@email.com";

        // WHEN
        final var email = Email.of(value);

        // THEN
        assertThat(value).isEqualTo(email.value());
    }

    @DisplayName("입력 값이 비어있을 경우 이메일 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void Given_NullOrBlank_When_Create_Then_ThrowException(final String value) {
        // WHEN
        final Executable when = () -> Email.of(value);

        // THEN
        assertThrows(InvalidArgumentException.class, when);
    }

    @DisplayName("잘못된 형식의 입력 값인 경우 이메일 생성을 실패한다.")
    @CsvSource({"email@ email.com", "email@emali.co.kr", "email@", "@email.com"})
    @ParameterizedTest
    void Given_InvalidFormed_When_Create_Then_ThrowException(final String value) {
        // WHEN
        final Executable when = () -> Email.of(value);

        // THEN
        assertThrows(InvalidArgumentException.class, when);
    }

}