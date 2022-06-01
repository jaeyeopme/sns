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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GeneralAccountService implements AccountService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long create(final AccountRequest request) {
        if (existsByEmail(request.email())) {
            throw new DuplicateEmailException();
        }

        if (existsByPhone(request.phone())) {
            throw new DuplicatePhoneException();
        }

        return userRepository.save(User.of(Account.of(request))).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(final Email email) {
        return userRepository.existsByAccountEmailValue(email.getValue());
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByPhone(final Phone phone) {
        return userRepository.existsByAccountPhoneValue(phone.getValue());
    }

}
