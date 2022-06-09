package me.jaeyeopme.sns.user.infrastructure;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Phone;
import me.jaeyeopme.sns.user.domain.User;
import me.jaeyeopme.sns.user.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class GeneralUserRepository implements UserRepository {

    private final CrudUserRepository jpaRepository;

    @Override
    public User create(final User user) {
        return jpaRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(final Email email) {
        return jpaRepository.findByAccountEmailValue(email.value());
    }

    @Override
    public boolean existsByEmail(final Email email) {
        return jpaRepository.existsByAccountEmailValue(email.value());
    }

    @Override
    public boolean existsByPhone(final Phone phone) {
        return jpaRepository.existsByAccountPhoneValue(phone.value());
    }

}
