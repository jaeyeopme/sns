package me.jaeyeopme.sns.domain.user.record;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record AccountRequest(
    @NotBlank @Pattern(regexp = EMAIL_REGEXP, message = "must be a well-formed email address") String email,
    @NotBlank @Pattern(regexp = PHONE_REGEXP, message = "must be a well-formed phone number") String phone,
    @NotBlank @Pattern(regexp = PASSWORD_REGEXP, message = "must be a well-formed password") String password,
    @NotBlank String name,
    String bio) {

    private static final String EMAIL_REGEXP = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";

    private static final String PHONE_REGEXP = "^(\\+[1-9][0-9]*(\\([0-9]*\\)|-[0-9]*-))?[0]?[1-9][0-9\\- ]*$";

    private static final String PASSWORD_REGEXP = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

}