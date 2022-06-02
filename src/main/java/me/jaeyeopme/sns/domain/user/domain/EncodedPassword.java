package me.jaeyeopme.sns.domain.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class EncodedPassword {

    @Column(name = "password", nullable = false)
    private String value;

    private EncodedPassword(final String value) {
        this.value = value;
    }

    public static EncodedPassword of(final String value) {
        return new EncodedPassword(value);
    }

}
