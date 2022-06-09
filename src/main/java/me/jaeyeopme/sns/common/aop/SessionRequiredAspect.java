package me.jaeyeopme.sns.common.aop;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.session.application.SessionFacade;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Aspect
@Component
public class SessionRequiredAspect {

    private final SessionFacade sessionFacade;

    @Before("@annotation(me.jaeyeopme.sns.common.aop.SessionRequired)")
    public void beforeSessionRequired() {
        sessionFacade.getAccount().email().value();
    }

}
