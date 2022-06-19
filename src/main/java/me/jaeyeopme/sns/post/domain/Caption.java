package me.jaeyeopme.sns.post.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Caption {

    @JsonProperty("caption")
    @Size(max = 2000, message = "최대 {max}자 까지만 입력할 수 있습니다.")
    @Column(name = "caption", length = 2000)
    private String value;

}
