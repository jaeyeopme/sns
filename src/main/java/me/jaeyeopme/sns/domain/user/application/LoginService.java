package me.jaeyeopme.sns.domain.user.application;

import me.jaeyeopme.sns.domain.user.record.UserLoginRequest;

public interface LoginService {

    void login(UserLoginRequest request);

    void logout();

}
