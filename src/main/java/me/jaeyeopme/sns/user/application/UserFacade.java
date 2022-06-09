package me.jaeyeopme.sns.user.application;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.security.PasswordEncryptor;
import me.jaeyeopme.sns.common.security.dto.RawPassword;
import me.jaeyeopme.sns.user.application.service.UserService;
import me.jaeyeopme.sns.user.domain.Account;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Phone;
import me.jaeyeopme.sns.user.presentation.dto.EmailRequest;
import me.jaeyeopme.sns.user.presentation.dto.PhoneRequest;
import me.jaeyeopme.sns.user.presentation.dto.UserCreateRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserService userService;

    private final PasswordEncryptor passwordEncryptor;

    public Long create(final UserCreateRequest request) {
        final var account = Account.of(request);
        final var rawPassword = RawPassword.of(request.password());

        userService.verifyDuplicatedEmail(account.email());
        userService.verifyDuplicatedPhone(account.phone());

        account.password(passwordEncryptor.encode(rawPassword));

        return userService.create(account);
    }

    public void verifyDuplicatedEmail(final EmailRequest request) {
        userService.verifyDuplicatedEmail(Email.of(request.email()));
    }

    public void verifyDuplicatedPhone(final PhoneRequest request) {
        userService.verifyDuplicatedPhone(Phone.of(request.phone()));
    }

}
