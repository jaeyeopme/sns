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

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

    @Column(name = "password", nullable = false)
    private String value;

    private Password(final String value) {
        verify(value);
        this.value = value;
    }

    public static Password of(final String value) {
        return new Password(value);
    }

    private void verify(final String value) {
        if (!StringUtils.hasText(value) || !PASSWORD_PATTERN.matcher(value).matches()) {
            throw new InvalidArgumentException();
        }
    }

}
