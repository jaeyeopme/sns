package me.jaeyeopme.sns.domain.user.application;

import me.jaeyeopme.sns.domain.user.domain.EncodedPassword;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;

public interface AccountPasswordEncoder {

    EncodedPassword encode(RawPassword rawPassword);

    boolean matches(RawPassword rawPassword, EncodedPassword encodedPassword);

}
