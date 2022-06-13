package me.jaeyeopme.sns.support;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import me.jaeyeopme.sns.common.aop.SessionRequiredAspect;
import me.jaeyeopme.sns.session.application.SessionFacade;
import me.jaeyeopme.sns.session.presentation.SessionRestController;
import me.jaeyeopme.sns.user.application.UserFacade;
import me.jaeyeopme.sns.user.presentation.UserRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Import({
    AopAutoConfiguration.class,
    SessionRequiredAspect.class
})
@WebMvcTest({
    SessionRestController.class,
    UserRestController.class
})
public abstract class RestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserFacade userFacade;

    @MockBean
    protected SessionFacade sessionFacade;

    @SneakyThrows
    protected String createJson(final Object value) {
        return objectMapper.writeValueAsString(value);
    }

    @SneakyThrows
    protected ResultActions performPost(
        final String urlTemplate,
        final Object content
    ) {
        return mockMvc.perform(
                post(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createJson(content)))
            .andDo(print());
    }

    @SneakyThrows
    protected ResultActions performGet(
        final String urlTemplate,
        final Object... uriVar
    ) {
        return mockMvc.perform(
                get(urlTemplate, uriVar)
                    .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print());
    }

    @SneakyThrows
    protected ResultActions performDelete(
        final String urlTemplate
    ) {
        return mockMvc.perform(
                delete(urlTemplate))
            .andDo(print());
    }

}
