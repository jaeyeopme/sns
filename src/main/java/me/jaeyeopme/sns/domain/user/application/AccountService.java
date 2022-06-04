package me.jaeyeopme.sns.domain.user.application;

import javax.servlet.http.HttpSession;
import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.record.AccountCreateRequest;
import me.jaeyeopme.sns.domain.user.record.AccountLoginRequest;

public interface AccountService {

    void login(AccountLoginRequest request, HttpSession session);

    Long create(AccountCreateRequest request);

    void verifyDuplicatedEmail(Email email);

    void verifyDuplicatedPhone(Phone phone);

}
