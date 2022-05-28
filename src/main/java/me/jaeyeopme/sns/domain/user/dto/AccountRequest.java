package me.jaeyeopme.sns.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountRequest {

    private static final String EMAIL_REGEXP = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";

    private static final String PHONE_REGEXP = "^(\\+[1-9][0-9]*(\\([0-9]*\\)|-[0-9]*-))?[0]?[1-9][0-9\\- ]*$";

    private static final String PASSWORD_REGEXP = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    @NotBlank
    @Pattern(regexp = EMAIL_REGEXP, message = "must be a well-formed email address")
    private String email;

    @NotBlank
    @Pattern(regexp = PHONE_REGEXP, message = "must be a well-formed phone number")
    private String phone;

    @NotBlank
    @Pattern(regexp = PASSWORD_REGEXP, message = "must be a well-formed password")
    private String password;

    @NotBlank
    private String name;

    private String bio;

}