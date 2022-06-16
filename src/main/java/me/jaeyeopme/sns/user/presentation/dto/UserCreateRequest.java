package me.jaeyeopme.sns.user.presentation.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.user.domain.Bio;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.domain.Name;
import me.jaeyeopme.sns.user.domain.Phone;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateRequest {

    @JsonUnwrapped
    @Valid
    private Email email;

    @JsonUnwrapped
    @Valid
    private Phone phone;

    @JsonUnwrapped
    @Valid
    private RawPassword password;

    @JsonUnwrapped
    @Valid
    private Name name;

    @JsonUnwrapped
    @Valid
    private Bio bio;

}
