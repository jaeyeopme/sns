package me.jaeyeopme.sns.domain.user.domain.embeded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Phone {

    @Column(name = "phone", nullable = false, unique = true)
    private String value;

    public static Phone of(final String value) {
        return new Phone(value);
    }
    
}
