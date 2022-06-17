package me.jaeyeopme.sns.session.presentation.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.user.domain.Email;
import me.jaeyeopme.sns.user.presentation.dto.RawPassword;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionCreateRequest {

    @JsonUnwrapped
    @Valid
    private Email email;

    @JsonUnwrapped
    @Valid
    private RawPassword password;

}
