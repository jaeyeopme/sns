package me.jaeyeopme.sns.unit.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.support.ConstraintViolationTest;
import me.jaeyeopme.sns.user.domain.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NameTest extends ConstraintViolationTest {

    @DisplayName("입력 값이 비어있지 않을 경우 이름을 생성한다.")
    @Test
    void create() {
        // GIVEN
        final var value = "name";
        final var name = Name.of(value);

        // WHEN
        final var when = validate(name);

        // THEN
        assertThat(when.isEmpty()).isTrue();
        assertThat(value).isEqualTo(name.value());
    }

    @DisplayName("입력 값이 비어있을 경우 이름 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void Given_NullOrBlank_When_Create_Then_ThrowException(final String value) {
        // GIVEN
        final var name = Name.of(value);

        // WHEN
        final var when = validate(name);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

}