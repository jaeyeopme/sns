package me.jaeyeopme.sns.session.application;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.security.PasswordEncryptor;
import me.jaeyeopme.sns.session.application.service.SessionService;
import me.jaeyeopme.sns.session.domain.Principal;
import me.jaeyeopme.sns.session.presentation.dto.SessionCreateRequest;
import me.jaeyeopme.sns.user.application.service.UserService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SessionFacade {

    private final SessionService sessionService;

    private final UserService userService;

    private final PasswordEncryptor passwordEncryptor;

    public void create(final SessionCreateRequest request) {
        final var user = userService.findByEmail(request.email());
        passwordEncryptor.matches(request.password(),
            user.password());
        sessionService.create(Principal.of(user.id(), user.email()));
    }

    public void invalidate() {
        sessionService.invalidate();
    }

    public Principal getPrincipal() {
        return sessionService.getPrincipal();
    }

}
