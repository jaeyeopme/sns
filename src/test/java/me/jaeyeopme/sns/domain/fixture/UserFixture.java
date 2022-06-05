package me.jaeyeopme.sns.domain.fixture;


import me.jaeyeopme.sns.domain.user.domain.Account;
import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.EncodedPassword;
import me.jaeyeopme.sns.domain.user.domain.Name;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;
import me.jaeyeopme.sns.domain.user.domain.User;
import me.jaeyeopme.sns.domain.user.record.UserCreateRequest;
import me.jaeyeopme.sns.domain.user.record.UserLoginRequest;

public class UserFixture {

    public final static Email EMAIL = Email.of("email@email.com");

    public final static Phone PHONE = Phone.of("+821012345678");

    public final static RawPassword RAW_PASSWORD = RawPassword.of("rawPassword1234");

    public final static EncodedPassword ENCODED_PASSWORD = EncodedPassword.of(
        "encodedPassword1234");

    public final static Name NAME = Name.of("name");

    public final static String BIO = "bio";

    public final static UserLoginRequest USER_LOGIN_REQUEST = new UserLoginRequest(
        EMAIL,
        RAW_PASSWORD);

    public final static UserCreateRequest USER_CREATE_REQUEST = new UserCreateRequest(
        EMAIL,
        PHONE,
        RAW_PASSWORD,
        NAME,
        BIO);

    public final static Account ACCOUNT = Account.of(USER_CREATE_REQUEST, ENCODED_PASSWORD);

    public static User USER = User.of(ACCOUNT);

}
