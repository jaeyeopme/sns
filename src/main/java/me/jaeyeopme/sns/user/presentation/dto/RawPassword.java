package me.jaeyeopme.sns.user.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RawPassword {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "잘못된 비밀번호 형식입니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @JsonProperty("password")
    private CharSequence value;

    public static RawPassword from(final CharSequence value) {
        return new RawPassword(value);
    }

}
