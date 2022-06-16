package me.jaeyeopme.sns.support.fixture;

import me.jaeyeopme.sns.session.presentation.dto.SessionCreateRequest;

public class SessionFixture {

    public final static SessionCreateRequest SESSION_CREATE_REQUEST = new SessionCreateRequest(
        UserFixture.EMAIL,
        UserFixture.RAW_PASSWORD);


}
