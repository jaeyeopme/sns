package me.jaeyeopme.sns.domain.user.domain.embeded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {

    @Column(name = "email", nullable = false, unique = true)
    private String value;

    public static Email of(final String value) {
        return new Email(value);
    }

}
