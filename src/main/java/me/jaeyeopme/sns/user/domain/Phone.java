package me.jaeyeopme.sns.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
public class Phone {

    @Pattern(regexp = "^\\+\\d{12}", message = "잘못된 전화번호 형식입니다.")
    @NotBlank(message = "전화번호를 입력해주세요.")
    @JsonProperty("phone")
    @Column(name = "phone", nullable = false, unique = true)
    private String value;

    public static Phone from(final String value) {
        return new Phone(value);
    }

}
