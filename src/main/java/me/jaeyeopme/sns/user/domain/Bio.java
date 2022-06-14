package me.jaeyeopme.sns.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.common.exception.InvalidArgumentException;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Bio {

    @Column(name = "bio")
    private String value;

    private Bio(final String value) {
        verify(value);
        this.value = value;
    }

    public static Bio of(final String value) {
        return new Bio(value);
    }

    private void verify(final String value) {
        if (StringUtils.hasText(value) && value.length() > 50) {
            throw new InvalidArgumentException();
        }
    }

}
