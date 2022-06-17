package me.jaeyeopme.sns.unit.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.support.ConstraintViolationTest;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import me.jaeyeopme.sns.user.presentation.dto.RawPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class RawPasswordTest extends ConstraintViolationTest {

    @DisplayName("입력 값이 올바른 경우 생성을 성공한다.")
    @Test
    void 입력_값이_올바른_경우_생성_성공() {
        // GIVEN
        final var rawPassword = UserFixture.RAW_PASSWORD;

        // WHEN
        final var when = validate(rawPassword);

        // THEN
        assertThat(when.isEmpty()).isTrue();
    }

    @DisplayName("비밀번호가 비어있을 경우 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void 비밀번호가_비어있을_경우_생성_실패(final String value) {
        // GIVEN
        final var rawPassword = RawPassword.from(value);

        // WHEN
        final var when = validate(rawPassword);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

    @DisplayName("비밀번호가 잘못된 형식인 경우 생성을 실패한다.")
    @CsvSource({"pass123", "password", "1234", "@asdf", "@1234"})
    @ParameterizedTest
    void 비밀번호가_잘못된_형식인_경우_생성_실패(final CharSequence value) {
        // GIVEN
        final var rawPassword = RawPassword.from(value);

        // WHEN
        final var when = validate(rawPassword);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

}
