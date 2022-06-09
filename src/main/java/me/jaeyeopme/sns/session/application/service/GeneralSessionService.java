package me.jaeyeopme.sns.session.application.service;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.session.domain.Principal;
import me.jaeyeopme.sns.session.domain.repository.SessionRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GeneralSessionService implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public void create(final Principal principal) {
        sessionRepository.create(principal);
    }

    @Override
    public void invalidate() {
        sessionRepository.invalidate();
    }

    @Override
    public Principal getPrincipal() {
        return sessionRepository.getPrincipal();
    }

}
