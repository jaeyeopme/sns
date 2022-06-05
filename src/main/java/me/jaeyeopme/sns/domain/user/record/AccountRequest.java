package me.jaeyeopme.sns.domain.user.record;

import me.jaeyeopme.sns.domain.user.domain.Email;
import me.jaeyeopme.sns.domain.user.domain.Name;
import me.jaeyeopme.sns.domain.user.domain.Phone;
import me.jaeyeopme.sns.domain.user.domain.RawPassword;

public record AccountRequest(Email email, Phone phone, RawPassword password,
                             Name name, String bio) {

    public AccountRequest(final String email, final String phone, final CharSequence password,
        final String name, final String bio) {
        this(Email.of(email), Phone.of(phone), RawPassword.of(password), Name.of(name), bio);
    }

}
