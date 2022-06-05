package me.jaeyeopme.sns.domain.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import me.jaeyeopme.sns.domain.user.exception.InvalidArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NameTest {

    @DisplayName("입력 값이 비어있지 않을 경우 이름을 생성한다.")
    @Test
    void create() {
        // GIVEN
        final var value = "name";

        // WHEN
        final var name = Name.of(value);

        // THEN
        assertThat(value).isEqualTo(name.getValue());
    }

    @DisplayName("입력 값이 비어있을 경우 이름 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void Given_NullOrBlank_When_Create_Then_ThrowException(final String value) {
        // WHEN
        final Executable when = () -> Name.of(value);

        // THEN
        assertThrows(InvalidArgumentException.class, when);
    }

}