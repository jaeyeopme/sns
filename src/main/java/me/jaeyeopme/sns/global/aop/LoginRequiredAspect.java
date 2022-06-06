package me.jaeyeopme.sns.global.aop;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.application.impl.SessionLoginService;
import me.jaeyeopme.sns.global.exception.LoginRequiredException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Aspect
@Component
public class LoginRequiredAspect {

    private final HttpSession httpSession;

    @Before("@annotation(me.jaeyeopme.sns.global.aop.LoginRequired)")
    public void beforeRequiredLogin() {

        if (httpSession.getAttribute(SessionLoginService.SESSION_NAME) == null) {
            throw new LoginRequiredException();
        }

    }

}
