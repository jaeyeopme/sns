package me.jaeyeopme.sns.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.resolver.SessionArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SessionArgumentResolver sessionArgumentResolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(sessionArgumentResolver);
    }

}
