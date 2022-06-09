package me.jaeyeopme.sns.session.domain.repository;

import me.jaeyeopme.sns.session.domain.Principal;

public interface SessionRepository {

    void create(Principal account);

    void invalidate();

    Principal getPrincipal();

}
