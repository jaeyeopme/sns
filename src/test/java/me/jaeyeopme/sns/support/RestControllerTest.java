package me.jaeyeopme.sns.support;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;

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

    @MockBean
    protected UserFacade userFacade;

    @MockBean
    protected SessionFacade sessionFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    protected String createJson(final Object value) {
        return objectMapper.writeValueAsString(value);
    }

}
