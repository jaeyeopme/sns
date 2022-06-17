package me.jaeyeopme.sns.unit.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.support.ConstraintViolationTest;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import me.jaeyeopme.sns.user.domain.Phone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class PhoneTest extends ConstraintViolationTest {

    @DisplayName("전화번호가 올바른 형식인 경우 생성을 성공한다.")
    @Test
    void 전화번호가_올바른_형식인_경우_생성_성공() {
        // GIVEN
        final var phone = UserFixture.PHONE;

        // WHEN
        final var when = validate(phone);

        // THEN
        assertThat(when.isEmpty()).isTrue();
    }

    @DisplayName("전화번호가 비어있을 경우 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void 전화번호가_비어있을_경우_생성_실패(final String value) {
        // GIVEN
        final var phone = Phone.from(value);

        // WHEN
        final var when = validate(phone);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

    @DisplayName("전화번호가 잘못된 형식인 경우 생성에 실패한다.")
    @CsvSource({"+82 010-1234-5678", "+82 010 1234 5678",
        "01012345678", "010-1234-5678", "010 1234 5678"})
    @ParameterizedTest
    void 전화번호가_잘못된_형식인_경우_생성_실패(final String value) {
        // GIVEN
        final var phone = Phone.from(value);

        // WHEN
        final var when = validate(phone);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

}
