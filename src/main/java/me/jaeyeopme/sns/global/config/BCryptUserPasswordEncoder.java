package me.jaeyeopme.sns.global.config;

import me.jaeyeopme.sns.domain.user.domain.EncodedPassword;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUserPasswordEncoder implements UserPasswordEncoder {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public EncodedPassword encode(final RawPassword rawPassword) {
        return EncodedPassword.of(encoder.encode(rawPassword.getValue()));
    }

    @Override
    public boolean matches(final RawPassword rawPassword,
        final EncodedPassword encodedPassword) {
        return encoder.matches(rawPassword.getValue(), encodedPassword.getValue());
    }

}
