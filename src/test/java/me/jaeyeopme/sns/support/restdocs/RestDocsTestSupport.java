package me.jaeyeopme.sns.support.restdocs;

import me.jaeyeopme.sns.config.RestDocsConfig;
import me.jaeyeopme.sns.support.RestControllerTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

@Disabled
@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
public abstract class RestDocsTestSupport extends RestControllerTest {

    @Autowired
    protected RestDocumentationResultHandler restDocumentationResultHandler;

}
