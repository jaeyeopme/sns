package me.jaeyeopme.sns.unit.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.support.ConstraintViolationTest;
import me.jaeyeopme.sns.user.domain.Phone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PhoneTest extends ConstraintViolationTest {

    @DisplayName("입력 값이 +로 시작하고 12자리 숫자인 경우 전화번호를 생성한다.")
    @Test
    void create() {
        // GIVEN
        final var value = "+821012345678";
        final var phone = Phone.of(value);

        // WHEN
        final var when = validate(phone);

        // THEN
        assertThat(when.isEmpty()).isTrue();
        assertThat(value).isEqualTo(phone.value());
    }

    @DisplayName("입력 값이 비어있을 경우 전화번호 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void Given_NullOrBlank_When_Create_Then_ThrowException(final String value) {
        // GIVEN
        final var phone = Phone.of(value);

        // WHEN
        final var when = validate(phone);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

    @DisplayName("잘못된 형식의 입력 값인 경우 전화번호 생성을 실패한다.")
    @CsvSource({"+82 010-1234-5678", "+82 010 1234 5678",
        "01012345678", "010-1234-5678", "010 1234 5678"})
    @ParameterizedTest
    void Given_InvalidFormed_When_Create_Then_ThrowException(final String value) {
        // GIVEN
        final var phone = Phone.of(value);

        // WHEN
        final var when = validate(phone);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

}