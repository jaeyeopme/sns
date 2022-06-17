package me.jaeyeopme.sns.common.aspect;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.security.SessionContext;
import me.jaeyeopme.sns.session.application.SessionFacade;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SessionRequiredAspect {

    private final SessionFacade sessionFacade;

    @Before("@annotation(me.jaeyeopme.sns.common.annotation.SessionRequired)")
    public void beforeSessionRequired() {
        SessionContext.set(sessionFacade.getPrincipal());
    }

    @After("@annotation(me.jaeyeopme.sns.common.annotation.SessionRequired)")
    public void afterSessionRequired() {
        SessionContext.clear();
    }

}
