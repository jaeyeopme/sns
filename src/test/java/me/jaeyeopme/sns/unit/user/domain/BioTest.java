package me.jaeyeopme.sns.unit.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.support.ConstraintViolationTest;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import me.jaeyeopme.sns.user.domain.Bio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BioTest extends ConstraintViolationTest {

    @DisplayName("입력 값이 올바른 경우 생성을 성공한다.")
    @Test
    void 입력_값이_올바른_경우_생성_성공() {
        // GIVEN
        final var bio = UserFixture.BIO;

        // WHEN
        final var when = validate(bio);

        // THEN
        assertThat(when.isEmpty()).isTrue();
    }

    @DisplayName("소개가 50자 초과일 경우 생성을 실패한다.")
    @Test
    void 소개가_50자_초과일_경우_생성_실패() {
        // GIVEN
        final var value = """
            biobiobiobio
            biobiobiobio
            biobiobiobio
            biobiobiobio
            biobiobiobio
            """;
        final var bio = Bio.from(value);

        // WHEN
        final var when = validate(bio);

        // THEN
        assertThat(when.isEmpty()).isFalse();
    }

}
