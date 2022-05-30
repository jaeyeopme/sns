package me.jaeyeopme.sns.domain.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByAccountEmailValue(String email);

    boolean existsByAccountPhoneValue(String phone);

}
