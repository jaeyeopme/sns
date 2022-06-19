package me.jaeyeopme.sns.session.domain.repository;

import java.util.Optional;
import me.jaeyeopme.sns.session.domain.Principal;

public interface SessionRepository {

    void create(Principal account);

    void invalidate();

    Optional<Principal> principal();

}
