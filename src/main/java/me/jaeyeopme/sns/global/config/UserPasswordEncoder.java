package me.jaeyeopme.sns.global.config;

import me.jaeyeopme.sns.domain.user.domain.EncodedPassword;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;

public interface UserPasswordEncoder {

    EncodedPassword encode(RawPassword rawPassword);

    boolean matches(RawPassword rawPassword, EncodedPassword encodedPassword);

}
