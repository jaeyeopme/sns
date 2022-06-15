package me.jaeyeopme.sns.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class EncodedPassword {

    @NotBlank
    @Column(name = "password", nullable = false)
    private String value;

    public static EncodedPassword of(final String value) {
        return new EncodedPassword(value);
    }

}
