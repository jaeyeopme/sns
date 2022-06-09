package me.jaeyeopme.sns.session.application.service;

import me.jaeyeopme.sns.session.domain.Principal;

public interface SessionService {

    void create(Principal principal);

    void invalidate();

    Principal getPrincipal();

}
