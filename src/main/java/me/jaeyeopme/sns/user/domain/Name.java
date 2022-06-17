package me.jaeyeopme.sns.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
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
public class Name {

    @Size(max = 20, message = "최대 {max}자 까지만 입력할 수 있습니다.")
    @NotBlank(message = "이름을 입력해주세요.")
    @JsonProperty("name")
    @Column(name = "name", nullable = false, unique = true)
    private String value;

    public static Name from(final String value) {
        return new Name(value);
    }

}
