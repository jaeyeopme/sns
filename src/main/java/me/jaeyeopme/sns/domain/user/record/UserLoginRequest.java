package me.jaeyeopme.sns.domain.user.record;

import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;

public record UserLoginRequest(Email email, RawPassword password) {

    public UserLoginRequest(final String email,
        final String password) {
        this(Email.of(email), RawPassword.of(password));
    }

}
