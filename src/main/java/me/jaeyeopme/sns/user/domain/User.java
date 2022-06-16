package me.jaeyeopme.sns.user.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

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

    @Column(name = "photo")
    private String photo;

    public static User of(final UserCreateRequest request) {
        return User.builder()
            .email(request.email())
            .phone(request.phone())
            .name(request.name())
            .bio(request.bio())
            .build();
    }

}
