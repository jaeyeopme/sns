package me.jaeyeopme.sns.unit.session.presentation;

import static me.jaeyeopme.sns.config.RestDocsConfig.constraintField;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import me.jaeyeopme.sns.common.exception.InvalidSessionException;
import me.jaeyeopme.sns.common.exception.NotFoundEmailException;
import me.jaeyeopme.sns.common.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.session.presentation.SessionRestController;
import me.jaeyeopme.sns.session.presentation.dto.SessionCreateRequest;
import me.jaeyeopme.sns.support.fixture.SessionFixture;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import me.jaeyeopme.sns.support.restdocs.RestDocsTestSupport;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.presentation.dto.RawPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class SessionRestControllerTest extends RestDocsTestSupport {

    @SneakyThrows
    @DisplayName("세션 생성 시 이메일이 존재하지 않은 경우 실패하고 HTTP 404를 반환한다.")
    @Test
    void 세션_생성_실패_이메일이_존재하지_않은_경우() {
        // GIVEN
        final var request = SessionFixture.SESSION_CREATE_REQUEST;
        willThrow(new NotFoundEmailException()).given(sessionFacade).create(request);

        // WHEN
        final var when = mockMvc.perform(
            post(SessionRestController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson(request)));

        // THEN
        when.andExpectAll(status().isNotFound());
        then(sessionFacade).should(only()).create(request);
    }

    @SneakyThrows
    @DisplayName("세션 생성 시 비밀번호가 일치하지 않은 경우 실패하고 HTTP 401을 반환한다.")
    @Test
    void 세션_생성_실패_비밀번호가_일치하지_않은_경우() {
        // GIVEN
        final var request = SessionFixture.SESSION_CREATE_REQUEST;
        willThrow(new NotMatchesPasswordException()).given(sessionFacade).create(request);

        // WHEN
        final var when = mockMvc.perform(
            post(SessionRestController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson(request)));

        // THEN
        when.andExpectAll(status().isUnauthorized());
        then(sessionFacade).should(only()).create(request);
    }

    @SneakyThrows
    @DisplayName("세션 생성 시 입력 값이 올바른 경우 HTTP 201을 반환한다.")
    @Test
    void 세션_생성_성공() {
        // GIVEN
        final var request = SessionFixture.SESSION_CREATE_REQUEST;

        // WHEN
        final var when = mockMvc.perform(
            post(SessionRestController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson(request)));

        // THEN
        when.andExpectAll(status().isCreated());
        then(sessionFacade).should(only()).create(request);

        // DOCS
        when.andDo(document(
            requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                    .attributes(constraintField("이메일 형식")),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                    .attributes(constraintField("최소 8자, 최소 하나의 문자 및 하나의 숫자"))
            )
        ));
    }

    @SneakyThrows
    @DisplayName("세션 생성 시 입력 값이 올바르지 않은 경우 HTTP 400을 반환한다.")
    @Test
    void 세션_생성_실패_입력_값이_올바르지_않은_경우() {
        // GIVEN
        final var email = Email.from("wrong email");
        final var password = RawPassword.from("wrong password");
        final var request = new SessionCreateRequest(email, password);

        // WHEN
        final var when = mockMvc.perform(
            post(SessionRestController.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson(request)));

        // THEN
        when.andExpectAll(status().isBadRequest());
        then(sessionFacade).should(never()).create(request);

        // DOCS
        when.andDo(document(
            responseFields(
                fieldWithPath("result").type(JsonFieldType.STRING)
                    .description("성공 SUCCESS, 실패 FAILURE"),
                fieldWithPath("data").type(JsonFieldType.STRING)
                    .description("성공 반환 데이터").optional(),
                fieldWithPath("code").type(JsonFieldType.STRING)
                    .description("서버 정의 에러 코드").optional(),
                fieldWithPath("reason").type(JsonFieldType.STRING)
                    .description("에러 메시지").optional(),
                fieldWithPath("errors").type(JsonFieldType.ARRAY)
                    .description("필드 바인딩 에러").optional(),
                fieldWithPath("errors[].field").type(JsonFieldType.STRING)
                    .description("필드 이름").optional(),
                fieldWithPath("errors[].value").type(JsonFieldType.STRING)
                    .description("필드 값").optional(),
                fieldWithPath("errors[].reason").type(JsonFieldType.STRING)
                    .description("필드 에러 메시지").optional()
            )
        ));
    }

    @SneakyThrows
    @DisplayName("세션 만료 시 유효하지 않은 세션인 경우 실패하고 HTTP 401을 반환한다.")
    @Test
    void 세션_만료_실패_유효하지_않은_세션인_경우() {
        // GIVEN
        willThrow(new InvalidSessionException()).given(sessionFacade).principal();

        // WHEN
        final var when = mockMvc.perform(
            delete(SessionRestController.URL)
                .header(HttpHeaders.COOKIE, "INVALID_SNS_SESSION"));

        // THEN
        when.andExpectAll(status().isUnauthorized());
        then(sessionFacade).should(only()).principal();
    }

    @SneakyThrows
    @DisplayName("세션 만료 시 유효한 세션인 경우 성공하고 HTTP 200을 반환한다.")
    @Test
    void 세션_만료_성공() {
        // GIVEN
        final var principal = UserFixture.PRINCIPAL;
        given(sessionFacade.principal()).willReturn(principal);

        // WHEN
        final var when = mockMvc.perform(
            delete(SessionRestController.URL)
                .header(HttpHeaders.COOKIE, "SNS_SESSION"));

        // THEN
        when.andExpectAll(status().isOk());
        then(sessionFacade).should().principal();
        then(sessionFacade).should().invalidate();

        // DOCS
        when.andDo(document(
            requestHeaders(
                headerWithName(HttpHeaders.COOKIE).description("유효한 세션 ID")
            )
        ));
    }


}
