package me.jaeyeopme.sns.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jaeyeopme.sns.user.presentation.dto.UserCreateRequest;

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

    @Setter
    @Embedded
    private EncodedPassword password;

    @Embedded
    private Name name;

    @Embedded
    private Bio bio;

    @Column
    private String photo;
    
    public static Account of(final UserCreateRequest request) {
        return Account.builder()
            .email(Email.of(request.email()))
            .phone(Phone.of(request.phone()))
            .name(Name.of(request.name()))
            .bio(Bio.of(request.bio()))
            .build();
    }

}
