package me.jaeyeopme.sns.domain.user.application;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.domain.Account;
import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.exception.DuplicateEmailException;
import me.jaeyeopme.sns.domain.user.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.domain.user.record.AccountRequest;
import me.jaeyeopme.sns.global.config.UserPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GeneralAccountService implements AccountService {

    private final UserRepository userRepository;
    private final UserPasswordEncoder encoder;

    @Transactional
    @Override
    public Long create(final AccountRequest request) {
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
