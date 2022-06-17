package me.jaeyeopme.sns.common.security;

import me.jaeyeopme.sns.common.exception.NotMatchesPasswordException;
import me.jaeyeopme.sns.user.domain.EncodedPassword;
import me.jaeyeopme.sns.user.presentation.dto.RawPassword;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncryptor extends BCryptPasswordEncoder implements PasswordEncryptor {

    @Override
    public EncodedPassword encode(final RawPassword rawPassword) {
        return EncodedPassword.of(super.encode(rawPassword.value()));
    }

    @Override
    public void matches(final RawPassword rawPassword,
        final EncodedPassword encodedPassword) {
        if (!super.matches(rawPassword.value(), encodedPassword.value())) {
            throw new NotMatchesPasswordException();
        }
    }

}
