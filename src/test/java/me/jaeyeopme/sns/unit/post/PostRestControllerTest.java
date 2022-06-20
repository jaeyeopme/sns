package me.jaeyeopme.sns.unit.post;

import static me.jaeyeopme.sns.config.RestDocsConfig.constraintField;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import me.jaeyeopme.sns.post.presentation.PostRestController;
import me.jaeyeopme.sns.support.fixture.PostFixture;
import me.jaeyeopme.sns.support.fixture.UserFixture;
import me.jaeyeopme.sns.support.restdocs.RestDocsTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class PostRestControllerTest extends RestDocsTestSupport {

    @SneakyThrows
    @DisplayName("게시물 생성에 성공한다.")
    @Test
    void 게시물_생성_성공() {
        // GIVEN
        final var principal = UserFixture.PRINCIPAL;
        final var request = PostFixture.POST_CREATE_REQUEST;
        given(sessionFacade.principal()).willReturn(principal);
        given(sessionContext.principal()).willReturn(principal);
        given(postFacade.create(request, principal.id())).willReturn(principal.id());

        // WHEN
        final var when = mockMvc.perform(post(PostRestController.URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createJson(request)));

        // THEN
        when.andExpectAll(status().isCreated(),
            header().string(HttpHeaders.LOCATION,
                "%s/%s".formatted(PostRestController.URL, 1L)));
        then(postFacade).should().create(request, principal.id());

        // ASPECT
        then(sessionFacade).should().principal();
        then(sessionContext).should().principal(principal);

        // RESOLVER
        then(sessionContext).should().principal();

        // DOCS
        when.andDo(document(
            requestFields(
                fieldWithPath("caption").type(JsonFieldType.STRING).description("게시물에 대한 설명")
                    .optional().attributes(constraintField("2000자 이내"))
            )
        ));
    }

}
