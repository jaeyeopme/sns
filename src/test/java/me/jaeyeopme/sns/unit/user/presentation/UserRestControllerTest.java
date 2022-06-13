package me.jaeyeopme.sns.unit.user.presentation;

import static me.jaeyeopme.sns.config.RestDocsConfig.field;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import me.jaeyeopme.sns.common.exception.DuplicateEmailException;
import me.jaeyeopme.sns.common.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.support.restdocs.RestDocsTestSupport;
import me.jaeyeopme.sns.support.user.fixture.UserFixture;
import me.jaeyeopme.sns.user.presentation.UserRestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserRestControllerTest extends RestDocsTestSupport {

    @SneakyThrows
    @DisplayName("이메일이 중복되는 경우 실패하고 HTTP 409를 반환한다.")
    @Test
    void 회원_가입_실패_이메일이_중복되는_경우() {
        // GIVEN
        final var request = UserFixture.USER_CREATE_REQUEST;
        willThrow(new DuplicateEmailException()).given(userFacade).create(request);

        // WHEN
        final var when = performPost(UserRestController.URL, request);

        // THEN
        when.andExpectAll(status().isConflict(),
            status().reason(DuplicateEmailException.REASON));
        then(userFacade).should(only()).create(request);
    }

    @SneakyThrows
    @DisplayName("전화번호가 중복되는 경우 실패하고 HTTP 409를 반환한다.")
    @Test
    void 회원_가입_실패_전화번호_중복되는_경우() {
        // GIVEN
        final var request = UserFixture.USER_CREATE_REQUEST;
        willThrow(new DuplicatePhoneException()).given(userFacade).create(request);

        // WHEN
        final var when = performPost(UserRestController.URL, request);

        // THEN
        when.andExpectAll(status().isConflict(),
            status().reason(DuplicatePhoneException.REASON));
        then(userFacade).should(only()).create(request);
    }

    @SneakyThrows
    @DisplayName("입력 값이 올바른 경우 성공하고 HTTP 201을 반환한다.")
    @Test
    void 회원_가입_성공() {
        // GIVEN
        final var request = UserFixture.USER_CREATE_REQUEST;
        given(userFacade.create(request)).willReturn(1L);

        // WHEN
        final var when = performPost(UserRestController.URL, request);

        // THEN
        when.andExpectAll(status().isCreated(),
            header().string(HttpHeaders.LOCATION,
                "%s/%s".formatted(UserRestController.URL, 1L)));
        then(userFacade).should(only()).create(request);

        // DOCS
        when.andDo(restDocumentationResultHandler.document(
            requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                    .attributes(field("constraints", "이메일 형식")),
                fieldWithPath("phone").type(JsonFieldType.STRING).description("전화번호")
                    .attributes(field("constraints", "13자 이내")),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                    .attributes(field("constraints", "최소 8자, 최소 하나의 문자 및 하나의 숫자")),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                    .attributes(field("constraints", "20자 이내")),
                fieldWithPath("bio").type(JsonFieldType.STRING).description("소개")
                    .attributes(field("constraints", "50자 이내"))
                    .optional()
            )
        ));
    }

    @SneakyThrows
    @DisplayName("중복되는 경우 실패하고 HTTP 409를 반환한다.")
    @Test
    void 이메일_중복검사_실패() {
        // GIVEN
        final var request = UserFixture.EMAIL_REQUEST;
        willThrow(DuplicateEmailException.class).given(userFacade)
            .verifyDuplicatedEmail(request);

        // WHEN
        final var when = performGet(
            UserRestController.URL + "/email/{email}", request.email());

        // THEN
        when.andExpectAll(status().isConflict(),
            status().reason(DuplicateEmailException.REASON));
        then(userFacade).should(only())
            .verifyDuplicatedEmail(request);
    }

    @SneakyThrows
    @DisplayName("중복되지 않은 경우 성공하고 HTTP 200을 반환한다.")
    @Test
    void 이메일_중복검사_성공() {
        // GIVEN
        final var request = UserFixture.EMAIL_REQUEST;

        // WHEN
        final var when = performGet(
            UserRestController.URL + "/email/{email}", request.email());

        // THEN
        when.andExpectAll(status().isOk());
        then(userFacade).should(only()).verifyDuplicatedEmail(request);
    }

    @SneakyThrows
    @DisplayName("중복되는 경우 실패하고 HTTP 409를 반환한다.")
    @Test
    void 전화번호_중복검사_실패() {
        // GIVEN
        final var request = UserFixture.PHONE_REQUEST;
        willThrow(DuplicatePhoneException.class).given(userFacade)
            .verifyDuplicatedPhone(request);

        // WHEN
        final var when = performGet(
            UserRestController.URL + "/phone/{phone}", request.phone());

        // THEN
        when.andExpectAll(status().isConflict(),
            status().reason(DuplicatePhoneException.REASON));
        then(userFacade).should(only()).verifyDuplicatedPhone(request);
    }

    @SneakyThrows
    @DisplayName("중복되지 않은 경우 성공하고 HTTP 200을 반환한다.")
    @Test
    void 전화번호_중복검사_성공() {
        // GIVEN
        final var request = UserFixture.PHONE_REQUEST;

        // WHEN
        final var when = performGet(
            UserRestController.URL + "/phone/{phone}", request.phone());

        // THEN
        when.andExpectAll(status().isOk());
        then(userFacade).should(only()).verifyDuplicatedPhone(request);
    }

}
