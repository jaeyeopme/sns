package me.jaeyeopme.sns.user.application.service;

import me.jaeyeopme.sns.user.domain.Account;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Phone;
import me.jaeyeopme.sns.user.domain.User;

public interface UserService {

    Long create(Account account);

    User findByEmail(Email email);

    void verifyDuplicatedEmail(Email email);

    void verifyDuplicatedPhone(Phone phone);

}
