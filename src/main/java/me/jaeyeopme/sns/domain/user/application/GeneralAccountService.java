/**
 * @author jaeyeop.dev@gmail.com
 */
package me.jaeyeopme.sns.domain.user.application;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.domain.UserRepository;
import me.jaeyeopme.sns.domain.user.domain.embeded.Account;
import me.jaeyeopme.sns.domain.user.record.AccountRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GeneralAccountService implements AccountService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public User create(final AccountRequest request) {
        return userRepository.save(User.of(Account.of(request)));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(final String email) {
        return userRepository.existsByAccountEmailValue(email);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByPhone(final String phone) {
        return userRepository.existsByAccountPhoneValue(phone);
    }

}
