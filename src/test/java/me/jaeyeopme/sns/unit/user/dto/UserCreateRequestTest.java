package me.jaeyeopme.sns.unit.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.jaeyeopme.sns.support.ConstraintViolationTest;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserCreateRequestTest extends ConstraintViolationTest {

    @DisplayName("입력 값이 올바른 경우 생성을 성공한다.")
    @Test
    void 입력_값이_올바른_경우_생성_성공() {
        // GIVEN
        final var request = UserFixture.USER_CREATE_REQUEST;

        // WHEN
        final var when = validate(request);

        // THEN
        assertThat(when.isEmpty()).isTrue();
    }

}
