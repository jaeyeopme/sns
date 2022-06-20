package me.jaeyeopme.sns.common.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jaeyeopme.sns.common.security.SessionContext;
import me.jaeyeopme.sns.session.application.SessionFacade;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SessionRequiredAspect {

    private final SessionFacade sessionFacade;

    private final SessionContext sessionContext;

    @Before("@annotation(me.jaeyeopme.sns.common.annotation.SessionRequired)")
    public void beforeSessionRequired() {
        final var principal = sessionFacade.principal();
        sessionContext.principal(principal);
        log.debug("SessionRequired Aspect ID: {}, EMAIL: {}", principal.id(), principal.email());
    }

    @After("@annotation(me.jaeyeopme.sns.common.annotation.SessionRequired)")
    public void afterSessionRequired() {
        sessionContext.clear();
    }

}
