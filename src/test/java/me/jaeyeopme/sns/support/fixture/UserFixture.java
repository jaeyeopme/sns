package me.jaeyeopme.sns.support.fixture;


import me.jaeyeopme.sns.session.domain.Principal;
import me.jaeyeopme.sns.user.domain.Bio;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Name;
import me.jaeyeopme.sns.user.domain.Phone;
import me.jaeyeopme.sns.user.domain.User;
import me.jaeyeopme.sns.user.presentation.dto.RawPassword;
import me.jaeyeopme.sns.user.presentation.dto.UserCreateRequest;

public class UserFixture {

    public final static Email EMAIL = Email.from("email@email.com");

    public final static Phone PHONE = Phone.from("+821012345678");

    public final static RawPassword RAW_PASSWORD = RawPassword.from("password1234");

    public final static Name NAME = Name.from("name");

    public final static Bio BIO = Bio.from("bio");

    public final static UserCreateRequest USER_CREATE_REQUEST = new UserCreateRequest(
        EMAIL,
        PHONE,
        RAW_PASSWORD,
        NAME,
        BIO);

    public final static User USER = User.of(USER_CREATE_REQUEST);

    public final static Principal PRINCIPAL = Principal.of(1L, EMAIL);

}
