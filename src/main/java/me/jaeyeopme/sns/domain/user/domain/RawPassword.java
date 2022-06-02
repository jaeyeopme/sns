package me.jaeyeopme.sns.domain.user.domain;

import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.domain.user.exception.InvalidArgumentException;
import org.springframework.util.StringUtils;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RawPassword {

    private static final Pattern RAW_PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

    private CharSequence value;

    private RawPassword(final CharSequence value) {
        verify(value);
        this.value = value;
    }

    public static RawPassword of(final CharSequence value) {
        return new RawPassword(value);
    }

    private void verify(final CharSequence value) {
        if (!StringUtils.hasText(value) || !RAW_PASSWORD_PATTERN.matcher(value).matches()) {
            throw new InvalidArgumentException();
        }
    }

}
