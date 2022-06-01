package me.jaeyeopme.sns.domain.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import me.jaeyeopme.sns.domain.user.exception.InvalidArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PhoneTest {

    @DisplayName("입력 값이 +로 시작하고 12자리 숫자인 경우 전화번호를 생성한다.")
    @Test
    void create() {
        // GIVEN
        final var value = "+821012345678";

        // WHEN
        final var phone = Phone.of(value);

        // THEN
        assertThat(value).isEqualTo(phone.getValue());
    }

    @DisplayName("입력 값이 비어있을 경우 전화번호 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void Given_NullOrBlank_When_Create_Then_ThrowException(final String value) {
        // WHEN
        final Executable when = () -> Phone.of(value);

        // THEN
        assertThrows(InvalidArgumentException.class, when);
    }

    @DisplayName("잘못된 형식의 입력 값인 경우 전화번호 생성을 실패한다.")
    @CsvSource({"+82 010-1234-5678", "+82 010 1234 5678",
        "01012345678", "010-1234-5678", "010 1234 5678"})
    @ParameterizedTest
    void Given_InvalidFormed_When_Create_Then_ThrowException(final String value) {
        // WHEN
        final Executable when = () -> Phone.of(value);

        // THEN
        assertThrows(InvalidArgumentException.class, when);
    }

}