package me.jaeyeopme.sns.domain.user.application.impl;

import me.jaeyeopme.sns.domain.user.application.PasswordEncryptor;
import me.jaeyeopme.sns.domain.user.domain.EncodedPassword;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncryptor extends BCryptPasswordEncoder implements PasswordEncryptor {

    @Override
    public EncodedPassword encode(final RawPassword rawPassword) {
        return EncodedPassword.of(super.encode(rawPassword.getValue()));
    }

    @Override
    public boolean matches(final RawPassword rawPassword,
        final EncodedPassword encodedPassword) {
        return super.matches(rawPassword.getValue(), encodedPassword.getValue());
    }

}
