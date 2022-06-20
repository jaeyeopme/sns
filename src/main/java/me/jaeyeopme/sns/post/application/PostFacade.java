package me.jaeyeopme.sns.post.application;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.post.application.service.PostService;
import me.jaeyeopme.sns.post.domain.Post;
import me.jaeyeopme.sns.post.presentation.dto.PostCreateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacade {

    private final PostService postService;

    public Long create(final PostCreateRequest request,
        final Long ownerId) {
        return postService.create(Post.of(request, ownerId));
    }

}
