package me.jaeyeopme.sns.support.restdocs;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.StandardCharsets;
import me.jaeyeopme.sns.config.RestDocsConfig;
import me.jaeyeopme.sns.support.RestControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@Disabled
@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsTestSupport extends RestControllerTest {

    @Autowired
    private RestDocumentationResultHandler restDocumentationResultHandler;

    @BeforeEach
    void setUp(final WebApplicationContext context,
        final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
            .addFilters(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
            .alwaysDo(restDocumentationResultHandler)
            .alwaysDo(print())
            .build();
    }

    protected RestDocumentationResultHandler document(Snippet... snippets) {
        return restDocumentationResultHandler.document(snippets);
    }

}
