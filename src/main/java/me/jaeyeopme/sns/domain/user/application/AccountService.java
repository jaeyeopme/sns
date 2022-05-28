package me.jaeyeopme.sns.domain.user.application;

import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.dto.AccountRequest;

public interface AccountService {

    User create(AccountRequest request);

}