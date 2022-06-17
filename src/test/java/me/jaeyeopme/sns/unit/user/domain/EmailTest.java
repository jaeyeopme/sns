package me.jaeyeopme.sns.unit.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.support.ConstraintViolationTest;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import me.jaeyeopme.sns.user.domain.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class EmailTest extends ConstraintViolationTest {

    @DisplayName("이메일 형식이 올바른 경우 생성을 성공한다.")
    @Test
    void 이메일_형식이_올바른_경우_생성_성공() {
        // GIVEN
        final var email = UserFixture.EMAIL;

        // THEN
        final var when = validate(email);

        // WHEN
        assertThat(when.isEmpty()).isTrue();
    }

    @DisplayName("이메일이 비어있을 경우 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void 이메일이_비어있을_경우_생성_실패(final String value) {
        // GIVEN
        final var email = Email.from(value);

        // WHEN
        final var when = validate(email);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

    @DisplayName("이메일이 잘못된 형식인 경우 생성을 실패한다.")
    @CsvSource({"email@ email.com", "email@emali.co.kr", "email@", "@email.com"})
    @ParameterizedTest
    void 이메일이_잘못된_형식인_경우_생성_실패(final String value) {
        // GIVEN
        final var email = Email.from(value);

        // WHEN
        final var when = validate(email);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

}
