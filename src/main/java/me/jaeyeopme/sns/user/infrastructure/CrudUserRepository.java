package me.jaeyeopme.sns.user.infrastructure;

import java.util.Optional;
import me.jaeyeopme.sns.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface CrudUserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAccountEmailValue(String value);

    boolean existsByAccountEmailValue(String value);

    boolean existsByAccountPhoneValue(String value);

}
