package me.jaeyeopme.sns.unit.user.presentation;

import static me.jaeyeopme.sns.config.RestDocsConfig.constraintField;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import me.jaeyeopme.sns.common.exception.DuplicateEmailException;
import me.jaeyeopme.sns.common.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import me.jaeyeopme.sns.support.restdocs.RestDocsTestSupport;
import me.jaeyeopme.sns.user.presentation.UserRestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserRestControllerTest extends RestDocsTestSupport {

    @SneakyThrows
    @DisplayName("회원 가입 시 이메일이 중복되는 경우 실패하고 HTTP 409를 반환한다.")
    @Test
    void 회원_가입_실패_이메일이_중복되는_경우() {
        // GIVEN
        final var request = UserFixture.USER_CREATE_REQUEST;
        willThrow(new DuplicateEmailException()).given(userFacade).create(request);

        // WHEN
        final var when = mockMvc.perform(
            post(UserRestController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson(request)));

        // THEN
        when.andExpectAll(status().isConflict());
        then(userFacade).should(only()).create(request);
    }

    @SneakyThrows
    @DisplayName("회원 가입 시 전화번호가 중복되는 경우 실패하고 HTTP 409를 반환한다.")
    @Test
    void 회원_가입_실패_전화번호_중복되는_경우() {
        // GIVEN
        final var request = UserFixture.USER_CREATE_REQUEST;
        willThrow(new DuplicatePhoneException()).given(userFacade).create(request);

        // WHEN
        final var when = mockMvc.perform(
            post(UserRestController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson(request)));

        // THEN
        when.andExpectAll(status().isConflict());
        then(userFacade).should(only()).create(request);
    }

    @SneakyThrows
    @DisplayName("회원 가입 시 입력 값이 올바른 경우 성공하고 HTTP 201을 반환한다.")
    @Test
    void 회원_가입_성공() {
        // GIVEN
        final var request = UserFixture.USER_CREATE_REQUEST;
        given(userFacade.create(request)).willReturn(1L);

        // WHEN
        final var when = mockMvc.perform(
            post(UserRestController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson(request)));

        // THEN
        when.andExpectAll(status().isCreated(),
            header().string(HttpHeaders.LOCATION,
                "%s/%s".formatted(UserRestController.URL, 1L)));
        then(userFacade).should(only()).create(request);

        // DOCS
        when.andDo(document(
            requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                    .attributes(constraintField("이메일 형식")),
                fieldWithPath("phone").type(JsonFieldType.STRING).description("전화번호")
                    .attributes(constraintField("13자 이내")),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                    .attributes(constraintField("최소 8자, 최소 하나의 문자 및 하나의 숫자")),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                    .attributes(constraintField("20자 이내")),
                fieldWithPath("bio").type(JsonFieldType.STRING).description("소개")
                    .attributes(constraintField("50자 이내"))
                    .optional()
            )
        ));
    }

    @SneakyThrows
    @DisplayName("이메일이 중복되는 경우 실패하고 HTTP 409를 반환한다.")
    @Test
    void 이메일_중복검사_실패() {
        // GIVEN
        final var email = UserFixture.EMAIL;
        willThrow(new DuplicateEmailException()).given(userFacade)
            .verifyDuplicatedEmail(email);

        // WHEN
        final var when = mockMvc.perform(
            get(UserRestController.URL + "/email/{email}", email.value())
                .characterEncoding(StandardCharsets.UTF_8));

        // THEN
        when.andExpectAll(status().isConflict());
        then(userFacade).should(only())
            .verifyDuplicatedEmail(email);
    }

    @SneakyThrows
    @DisplayName("이메일이 중복되지 않은 경우 성공하고 HTTP 200을 반환한다.")
    @Test
    void 이메일_중복검사_성공() {
        // GIVEN
        final var email = UserFixture.EMAIL;
        willDoNothing().given(userFacade).verifyDuplicatedEmail(email);

        // WHEN
        final var when = mockMvc.perform(
            get(UserRestController.URL + "/email/{email}", email.value())
                .characterEncoding(StandardCharsets.UTF_8));

        // THEN
        when.andExpectAll(status().isOk());
        then(userFacade).should(only()).verifyDuplicatedEmail(email);
    }

    @SneakyThrows
    @DisplayName("전화번호가 중복되는 경우 실패하고 HTTP 409를 반환한다.")
    @Test
    void 전화번호_중복검사_실패() {
        // GIVEN
        final var phone = UserFixture.PHONE;
        willThrow(new DuplicatePhoneException()).given(userFacade)
            .verifyDuplicatedPhone(phone);

        // WHEN
        final var when = mockMvc.perform(
            get(UserRestController.URL + "/phone/{phone}", phone.value())
                .characterEncoding(StandardCharsets.UTF_8));

        // THEN
        when.andExpectAll(status().isConflict());
        then(userFacade).should(only()).verifyDuplicatedPhone(phone);
    }

    @SneakyThrows
    @DisplayName("전화번호가 중복되지 않은 경우 성공하고 HTTP 200을 반환한다.")
    @Test
    void 전화번호_중복검사_성공() {
        // GIVEN
        final var phone = UserFixture.PHONE;
        willDoNothing().given(userFacade).verifyDuplicatedPhone(phone);

        // WHEN
        final var when = mockMvc.perform(
            get(UserRestController.URL + "/phone/{phone}", phone.value())
                .characterEncoding(StandardCharsets.UTF_8));

        // THEN
        when.andExpectAll(status().isOk());
        then(userFacade).should(only()).verifyDuplicatedPhone(phone);
    }

}
