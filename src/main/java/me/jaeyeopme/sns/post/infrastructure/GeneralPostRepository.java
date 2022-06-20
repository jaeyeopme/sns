package me.jaeyeopme.sns.post.infrastructure;

import lombok.RequiredArgsConstructor;
import me.jaeyeopme.sns.post.domain.Post;
import me.jaeyeopme.sns.post.domain.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GeneralPostRepository implements PostRepository {

    private final CrudPostRepository crudPostRepository;

    @Override
    public Post create(final Post post) {
        return crudPostRepository.save(post);
    }

}
