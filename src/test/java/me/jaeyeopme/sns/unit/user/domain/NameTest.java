package me.jaeyeopme.sns.unit.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.support.ConstraintViolationTest;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import me.jaeyeopme.sns.user.domain.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class NameTest extends ConstraintViolationTest {

    @DisplayName("입력 값이 올바른 경우 생성을 성공한다.")
    @Test
    void 입력_값이_올바른_경우_생성_성공() {
        // GIVEN
        final var name = UserFixture.NAME;

        // WHEN
        final var when = validate(name);

        // THEN
        assertThat(when.isEmpty()).isTrue();
    }

    @DisplayName("이름이 비어있을 경우 생성을 실패한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void 이름이_비어있을_경우_생성_실패(final String value) {
        // GIVEN
        final var name = Name.from(value);

        // WHEN
        final var when = validate(name);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

    @DisplayName("이름이 20자 초과일 경우 생성을 실패한다.")
    @Test
    void 이름이_20자_초과일_경우_생성_실패() {
        // GIVEN
        final var value = """
            namenamename
            namenamename
            namenamename
            """;
        final var name = Name.from(value);
        // WHEN
        final var when = validate(name);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

}
