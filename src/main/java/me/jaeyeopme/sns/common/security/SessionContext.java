package me.jaeyeopme.sns.common.security;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.session.domain.Principal;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SessionContext {

    private final static ThreadLocal<Principal> sessionContext = new ThreadLocal<>();

    public Principal principal() {
        return sessionContext.get();
    }

    public void principal(final Principal principal) {
        sessionContext.set(principal);
    }

    public void clear() {
        sessionContext.remove();
    }

}
