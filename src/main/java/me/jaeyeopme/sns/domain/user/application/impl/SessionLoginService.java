package me.jaeyeopme.sns.domain.user.application.impl;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.application.LoginService;
import me.jaeyeopme.sns.domain.user.application.PasswordEncryptor;
import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.exception.NotFoundEmailException;
import me.jaeyeopme.sns.domain.user.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.domain.user.record.UserLoginRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SessionLoginService implements LoginService {

    public static final String SESSION_NAME = "USER_IDENTIFIER_ID";

    private final UserRepository userRepository;

    private final HttpSession session;

    private final PasswordEncryptor encryptor;

    @Transactional(readOnly = true)
    @Override
    public void login(final UserLoginRequest request) {
        final User user = userRepository.findByAccountEmailValue(
                request.email().getValue())
            .orElseThrow(NotFoundEmailException::new);

        if (!encryptor.matches(request.password(), user.getAccount().getPassword())) {
            throw new NotMatchesPasswordException();
        }

        session.setAttribute(SESSION_NAME, user.getId());
    }

    @Override
    public void logout() {
        session.invalidate();
    }

}
