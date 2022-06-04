package me.jaeyeopme.sns.domain.user.application;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.domain.Account;
import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.exception.DuplicateEmailException;
import me.jaeyeopme.sns.domain.user.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.domain.user.exception.NotFoundEmailException;
import me.jaeyeopme.sns.domain.user.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.domain.user.record.AccountCreateRequest;
import me.jaeyeopme.sns.domain.user.record.AccountLoginRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GeneralAccountService implements AccountService {

    private static final String SESSION_NAME = "USER_IDENTIFIER_ID";

    private final UserRepository userRepository;

    private final AccountPasswordEncoder encoder;

    @Transactional(readOnly = true)
    @Override
    public void login(final AccountLoginRequest request,
        final HttpSession session) {
        final User user = userRepository.findByAccountEmailValue(
                request.email().getValue())
            .orElseThrow(NotFoundEmailException::new);

        if (!encoder.matches(request.password(), user.getAccount().getPassword())) {
            throw new NotMatchesPasswordException();
        }

        session.setAttribute(SESSION_NAME, user.getId());
    }

    @Transactional
    @Override
    public Long create(final AccountCreateRequest request) {
        verifyDuplicatedEmail(request.email());
        verifyDuplicatedPhone(request.phone());

        final var encodedPassword = encoder.encode(request.password());
        return userRepository.save(User.of(Account.of(request, encodedPassword))).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public void verifyDuplicatedEmail(final Email email) {
        if (userRepository.existsByAccountEmailValue(email.getValue())) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void verifyDuplicatedPhone(final Phone phone) {
        if (userRepository.existsByAccountPhoneValue(phone.getValue())) {
            throw new DuplicatePhoneException();
        }
    }

}
