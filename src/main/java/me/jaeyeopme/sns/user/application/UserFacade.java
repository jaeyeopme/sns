package me.jaeyeopme.sns.user.application;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.security.PasswordEncryptor;
import me.jaeyeopme.sns.user.application.service.UserService;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Phone;
import me.jaeyeopme.sns.user.domain.User;
import me.jaeyeopme.sns.user.presentation.dto.UserCreateRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserService userService;

    private final PasswordEncryptor passwordEncryptor;

    public Long create(final UserCreateRequest request) {
        userService.verifyDuplicatedEmail(request.email());
        userService.verifyDuplicatedPhone(request.phone());

        final var user = User.of(request);
        user.password(passwordEncryptor.encode(request.password()));

        return userService.create(user);
    }

    public void verifyDuplicatedEmail(final Email email) {
        userService.verifyDuplicatedEmail(email);
    }

    public void verifyDuplicatedPhone(final Phone phone) {
        userService.verifyDuplicatedPhone(phone);
    }

}
