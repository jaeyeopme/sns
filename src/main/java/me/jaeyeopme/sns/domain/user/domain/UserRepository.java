package me.jaeyeopme.sns.domain.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountEmailValue(String values);

    boolean existsByAccountEmailValue(String value);

    boolean existsByAccountPhoneValue(String value);

}
