package me.jaeyeopme.sns.support.fixture;

import me.jaeyeopme.sns.post.domain.Caption;
import me.jaeyeopme.sns.post.presentation.dto.PostCreateRequest;

public class PostFixture {

    public static final Caption CAPTION = new Caption("caption");

    public static final PostCreateRequest POST_CREATE_REQUEST = new PostCreateRequest(CAPTION);

}
