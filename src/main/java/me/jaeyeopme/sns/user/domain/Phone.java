package me.jaeyeopme.sns.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Phone {

    @Pattern(regexp = "^\\+\\d{12}")
    @NotBlank
    @Column(name = "phone", nullable = false, unique = true)
    private String value;

    public static Phone of(final String value) {
        return new Phone(value);
    }

}
