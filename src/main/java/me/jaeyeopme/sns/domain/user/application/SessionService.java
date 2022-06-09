package me.jaeyeopme.sns.domain.user.application;

import me.jaeyeopme.sns.domain.user.domain.Account;
import me.jaeyeopme.sns.domain.user.record.SessionCreateRequest;

public interface SessionService {

    void create(SessionCreateRequest request);

    void invalidate();

    Account getInfo();

}
