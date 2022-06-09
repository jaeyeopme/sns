package me.jaeyeopme.sns.support.user.fixture;


import me.jaeyeopme.sns.common.security.dto.RawPassword;
import me.jaeyeopme.sns.session.domain.Principal;
import me.jaeyeopme.sns.session.presentation.dto.SessionCreateRequest;
import me.jaeyeopme.sns.user.domain.Account;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Name;
import me.jaeyeopme.sns.user.domain.Phone;
import me.jaeyeopme.sns.user.domain.User;
import me.jaeyeopme.sns.user.presentation.dto.EmailRequest;
import me.jaeyeopme.sns.user.presentation.dto.PhoneRequest;
import me.jaeyeopme.sns.user.presentation.dto.UserCreateRequest;

public class UserFixture {

    public final static Email EMAIL = Email.of("email@email.com");

    public final static Phone PHONE = Phone.of("+821012345678");

    public final static RawPassword RAW_PASSWORD = RawPassword.of("rawPassword1234");

    public final static Name NAME = Name.of("name");

    public final static String BIO = "bio";

    public final static SessionCreateRequest SESSION_CREATE_REQUEST = new SessionCreateRequest(
        EMAIL.value(),
        RAW_PASSWORD.value().toString());

    public final static UserCreateRequest USER_CREATE_REQUEST = new UserCreateRequest(
        EMAIL.value(),
        PHONE.value(),
        RAW_PASSWORD.value().toString(),
        NAME.value(),
        BIO);

    public final static Account ACCOUNT = Account.of(USER_CREATE_REQUEST);

    public final static User USER = User.of(ACCOUNT);

    public final static EmailRequest EMAIL_REQUEST = new EmailRequest(EMAIL.value());

    public final static PhoneRequest PHONE_REQUEST = new PhoneRequest(PHONE.value());

    public final static Principal PRINCIPAL = Principal.of(1L, EMAIL);

}
