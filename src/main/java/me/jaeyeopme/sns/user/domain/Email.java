package me.jaeyeopme.sns.user.domain;

import java.util.regex.Pattern;
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
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");

    @Column(name = "email", nullable = false, unique = true)
    private String value;

    private Email(final String value) {
        verify(value);
        this.value = value;
    }

    public static Email of(final String value) {
        return new Email(value);
    }

    private void verify(final String value) {
        if (!StringUtils.hasText(value) || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidArgumentException();
        }
    }

}
