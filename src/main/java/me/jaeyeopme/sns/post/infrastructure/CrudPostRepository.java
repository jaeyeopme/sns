package me.jaeyeopme.sns.post.infrastructure;

import me.jaeyeopme.sns.post.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface CrudPostRepository extends CrudRepository<Post, Long> {

}
