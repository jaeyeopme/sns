package me.jaeyeopme.sns.domain.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.domain.user.record.AccountRequest;

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
    private Password password;

    @Embedded
    private Name name;

    @Column
    private String photo;

    @Column
    private String bio;

    public static Account of(final AccountRequest request) {
        return Account.builder()
            .email(request.email())
            .phone(request.phone())
            .password(request.password())
            .name(request.name())
            .bio(request.bio())
            .build();
    }

}
