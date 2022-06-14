package me.jaeyeopme.sns.config;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.snippet.Attributes;

@TestConfiguration
public class RestDocsConfig {

    public static Attributes.Attribute field(final String key,
        final String value) {
        return new Attributes.Attribute(key, value);
    }

    @Bean
    public RestDocumentationResultHandler restDocumentationResultHandler() {
        return document(
            "{class_name}/{methodName}",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        );
    }

}
