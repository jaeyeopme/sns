package me.jaeyeopme.sns.common.resolver;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.annotation.SessionPrincipal;
import me.jaeyeopme.sns.common.security.SessionContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class SessionArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionContext sessionContext;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
        final ModelAndViewContainer mavContainer,
        final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
        throws Exception {
        return sessionContext.principal();
    }

}
