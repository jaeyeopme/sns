package me.jaeyeopme.sns.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.common.exception.InvalidArgumentException;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Name {

    @Column(name = "name", nullable = false, unique = true)
    private String value;

    private Name(final String value) {
        verify(value);
        this.value = value;
    }

    public static Name of(final String value) {
        return new Name(value);
    }

    private void verify(final String value) {
        if (!StringUtils.hasText(value)) {
            throw new InvalidArgumentException();
        }
    }

}
