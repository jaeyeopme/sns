package me.jaeyeopme.sns.session.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.user.domain.Email;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Principal {

    private Long id;

    private Email email;

    public static Principal of(final Long id,
        final Email email) {
        return new Principal(id, email);
    }

}
