package me.jaeyeopme.sns.common.security;

import me.jaeyeopme.sns.user.domain.EncodedPassword;
import me.jaeyeopme.sns.user.presentation.dto.RawPassword;

public interface PasswordEncryptor {

    EncodedPassword encode(RawPassword rawPassword);

    void matches(RawPassword rawPassword,
        EncodedPassword encodedPassword);

}
