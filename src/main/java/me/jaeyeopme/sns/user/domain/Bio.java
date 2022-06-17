package me.jaeyeopme.sns.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Bio {

    @Size(max = 50, message = "최대 {max}자 까지만 입력할 수 있습니다.")
    @JsonProperty("bio")
    @Column(name = "bio")
    private String value;

    public static Bio from(final String value) {
        return new Bio(value);
    }

}
