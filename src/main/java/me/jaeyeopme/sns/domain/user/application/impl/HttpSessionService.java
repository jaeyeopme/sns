package me.jaeyeopme.sns.domain.user.application.impl;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.application.PasswordEncryptor;
import me.jaeyeopme.sns.domain.user.application.SessionService;
import me.jaeyeopme.sns.domain.user.domain.Account;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.exception.InvalidSessionException;
import me.jaeyeopme.sns.domain.user.exception.NotFoundEmailException;
import me.jaeyeopme.sns.domain.user.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.domain.user.record.SessionCreateRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HttpSessionService implements SessionService {

    public static final String SESSION_NAME = "SNS_SESSION";

    private final UserRepository userRepository;

    private final PasswordEncryptor encryptor;

    private final HttpSession session;

    @Override
    public void create(final SessionCreateRequest request) {
        final var user = userRepository.findByAccountEmailValue(request.email().getValue())
            .orElseThrow(NotFoundEmailException::new);

        if (!encryptor.matches(request.password(), user.getAccount().getPassword())) {
            throw new NotMatchesPasswordException();
        }

        session.setAttribute(SESSION_NAME, user.getAccount());
    }

    @Override
    public void invalidate() {
        session.invalidate();
    }

    @Override
    public Account getInfo() {
        return (Account) Optional.ofNullable(session.getAttribute(SESSION_NAME))
            .orElseThrow(InvalidSessionException::new);
    }

}
