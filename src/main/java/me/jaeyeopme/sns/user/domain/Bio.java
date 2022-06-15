package me.jaeyeopme.sns.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Bio {

    @Size(max = 50)
    @Column(name = "bio")
    private String value;

    public static Bio of(final String value) {
        return new Bio(value);
    }

}
