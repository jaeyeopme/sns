package me.jaeyeopme.sns.domain.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.domain.user.record.UserCreateRequest;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Account {

    @Embedded
    private Email email;

    @Embedded
    private Phone phone;

    @Embedded
    private EncodedPassword password;

    @Embedded
    private Name name;

    @Column
    private String photo;

    @Column
    private String bio;

    public static Account of(final UserCreateRequest request,
        final EncodedPassword encodedPassword) {
        return Account.builder()
            .email(request.email())
            .phone(request.phone())
            .name(request.name())
            .bio(request.bio())
            .password(encodedPassword)
            .build();
    }

}
