package me.jaeyeopme.sns.common.security;

import me.jaeyeopme.sns.common.security.dto.RawPassword;
import me.jaeyeopme.sns.user.domain.EncodedPassword;

public interface PasswordEncryptor {

    EncodedPassword encode(RawPassword rawPassword);

    void matches(RawPassword rawPassword,
        EncodedPassword encodedPassword);

}
