package me.jaeyeopme.sns.post.application.service;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.post.domain.Post;
import me.jaeyeopme.sns.post.domain.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneralPostService implements PostService {

    private final PostRepository postRepository;

    @Transactional
    @Override
    public Long create(final Post post) {
        return postRepository.create(post).id();
    }

}
