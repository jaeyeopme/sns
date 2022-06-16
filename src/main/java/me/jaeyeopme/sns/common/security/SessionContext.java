package me.jaeyeopme.sns.common.security;

import me.jaeyeopme.sns.session.domain.Principal;

public class SessionContext {

    private final static ThreadLocal<Principal> sessionContext = new ThreadLocal<>();

    public static Principal get() {
        return sessionContext.get();
    }

    public static void set(final Principal principal) {
        sessionContext.set(principal);
    }

    public static void clear() {
        sessionContext.remove();
    }

}
