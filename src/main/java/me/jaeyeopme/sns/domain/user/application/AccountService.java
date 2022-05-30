package me.jaeyeopme.sns.domain.user.application;

import me.jaeyeopme.sns.domain.user.record.AccountRequest;

public interface AccountService {

    Long create(AccountRequest request);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
