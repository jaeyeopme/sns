package me.jaeyeopme.sns.domain.user.domain;

import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.domain.user.exception.InvalidArgumentException;
import org.springframework.util.StringUtils;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Phone {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+\\d{12}");

    @Column(name = "phone", nullable = false, unique = true)
    private String value;

    private Phone(final String value) {
        verify(value);
        this.value = value;
    }

    public static Phone of(final String value) {
        return new Phone(value);
    }

    private void verify(final String value) {
        if (!StringUtils.hasText(value) || !PHONE_PATTERN.matcher(value).matches()) {
            throw new InvalidArgumentException();
        }
    }

}
