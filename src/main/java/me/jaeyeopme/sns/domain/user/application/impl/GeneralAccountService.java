package me.jaeyeopme.sns.domain.user.application.impl;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.application.AccountService;
import me.jaeyeopme.sns.domain.user.application.PasswordEncryptor;
import me.jaeyeopme.sns.domain.user.domain.Account;
import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.exception.DuplicateEmailException;
import me.jaeyeopme.sns.domain.user.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.domain.user.record.UserCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GeneralAccountService implements AccountService {

    private final UserRepository userRepository;

    private final PasswordEncryptor encryptor;

    @Transactional
    @Override
    public Long create(final UserCreateRequest request) {
        verifyDuplicatedEmail(request.email());
        verifyDuplicatedPhone(request.phone());

        final var encodedPassword = encryptor.encode(request.password());
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
