package me.jaeyeopme.sns.domain.user.application;

import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.record.AccountRequest;

public interface AccountService {

    Long create(AccountRequest request);

    void verifyDuplicatedEmail(Email email);

    void verifyDuplicatedPhone(Phone phone);

}
