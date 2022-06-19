package me.jaeyeopme.sns.session.infrastructure;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.session.domain.Principal;
import me.jaeyeopme.sns.session.domain.repository.SessionRepository;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class InMemorySessionRepository implements SessionRepository {

    public static final String SESSION_NAME = "SNS_SESSION";

    private final HttpSession session;

    @Override
    public void create(final Principal principal) {
        session.setAttribute(SESSION_NAME, principal);
    }

    @Override
    public void invalidate() {
        session.invalidate();
    }

    @Override
    public Optional<Principal> principal() {
        return Optional.ofNullable((Principal) session.getAttribute(SESSION_NAME));
    }

}
