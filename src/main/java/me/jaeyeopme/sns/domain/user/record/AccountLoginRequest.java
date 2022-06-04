package me.jaeyeopme.sns.domain.user.record;

import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;

public record AccountLoginRequest(Email email, RawPassword password) {

    public AccountLoginRequest(final String email,
        final String password) {
        this(Email.of(email), RawPassword.of(password));
    }

}
