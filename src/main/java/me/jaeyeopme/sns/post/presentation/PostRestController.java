package me.jaeyeopme.sns.post.presentation;

import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.common.annotation.SessionPrincipal;
import me.jaeyeopme.sns.common.annotation.SessionRequired;
import me.jaeyeopme.sns.common.exception.dto.SNSResponse;
import me.jaeyeopme.sns.post.application.PostFacade;
import me.jaeyeopme.sns.post.presentation.dto.PostCreateRequest;
import me.jaeyeopme.sns.session.domain.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(PostRestController.URL)
@RestController
@RequiredArgsConstructor
public class PostRestController {

    public static final String URL = "/api/v1/posts";

    private final PostFacade postFacade;

    @SessionRequired
    @PostMapping
    public ResponseEntity<SNSResponse<Void>> create(
        @SessionPrincipal final Principal principal,
        @RequestBody @Valid final PostCreateRequest request) {
        final var postId = postFacade.create(request, principal.id());
        final var location = URI.create("%s/%s".formatted(URL, postId));

        return SNSResponse.create(location);
    }

}
