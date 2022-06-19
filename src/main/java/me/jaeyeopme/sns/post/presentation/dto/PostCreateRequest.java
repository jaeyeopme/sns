package me.jaeyeopme.sns.post.presentation.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaeyeopme.sns.post.domain.Caption;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateRequest {

    @JsonUnwrapped
    @Valid
    private Caption caption;

}
