package me.jaeyeopme.sns.global.aop;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.application.SessionService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Aspect
@Component
public class SessionRequiredAspect {

    private final SessionService sessionService;

    @Before("@annotation(me.jaeyeopme.sns.global.aop.SessionRequired)")
    public void beforeSessionRequired() {
        sessionService.getInfo().getEmail().getValue();
    }

}
