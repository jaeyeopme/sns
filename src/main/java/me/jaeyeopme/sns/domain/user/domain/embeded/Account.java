package me.jaeyeopme.sns.domain.user.domain.embeded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.domain.user.dto.AccountRequest;

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

    @Column(nullable = false)
    private String name;

    @Column
    private String photo;

    @Column
    private String bio;

    public static Account of(final AccountRequest request) {
        return Account.builder()
            .email(Email.of(request.getEmail()))
            .phone(Phone.of(request.getPhone()))
            .password(Password.of(request.getPassword()))
            .name(request.getName())
            .bio(request.getBio())
            .build();
    }

}
