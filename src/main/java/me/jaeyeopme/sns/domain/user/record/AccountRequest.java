package me.jaeyeopme.sns.domain.user.record;

import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.Name;
import me.jaeyeopme.sns.domain.user.domain.Password;
import me.jaeyeopme.sns.domain.user.domain.Phone;

public record AccountRequest(Email email, Phone phone, Password password, Name name, String bio) {

    public AccountRequest(final String email, final String phone, final String password,
        final String name, final String bio) {
        this(Email.of(email), Phone.of(phone), Password.of(password), Name.of(name), bio);
    }

}
