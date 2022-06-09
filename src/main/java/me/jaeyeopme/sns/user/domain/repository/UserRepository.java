package me.jaeyeopme.sns.user.domain.repository;

import java.util.Optional;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Phone;
import me.jaeyeopme.sns.user.domain.User;

public interface UserRepository {

    User create(User user);

    Optional<User> findByEmail(Email email);

    boolean existsByEmail(Email email);

    boolean existsByPhone(Phone phone);

}
