package me.jaeyeopme.sns.user.application.service;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.exception.DuplicateEmailException;
import me.jaeyeopme.sns.common.exception.DuplicatePhoneException;
import me.jaeyeopme.sns.common.exception.NotFoundEmailException;
import me.jaeyeopme.sns.user.domain.Account;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Phone;
import me.jaeyeopme.sns.user.domain.User;
import me.jaeyeopme.sns.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GeneralUserService implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long create(final Account account) {
        return userRepository.create(User.of(account)).id();
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmail(final Email email) {
        return userRepository.findByEmail(email)
            .orElseThrow(NotFoundEmailException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public void verifyDuplicatedEmail(final Email email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public void verifyDuplicatedPhone(final Phone phone) {
        if (userRepository.existsByPhone(phone)) {
            throw new DuplicatePhoneException();
        }
    }

}
