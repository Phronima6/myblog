package ru.yandex.practicum.post.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.post.dto.PostDtoRequest;
import ru.yandex.practicum.post.dto.PostDtoResponse;
import ru.yandex.practicum.post.dto.PostDtoResponseShort;
import ru.yandex.practicum.post.model.Post;

@Component
public class PostMapper {

    public Post toPost(final PostDtoRequest postDtoRequest) {
        final Post post = new Post();
        post.setPostName(postDtoRequest.getPostName());
        post.setPostText(postDtoRequest.getPostText());
        return post;
    }

    public PostDtoResponse toPostDtoResponse(final Post post) {
        final PostDtoResponse postDtoResponse = new PostDtoResponse();
        postDtoResponse.setPostId(post.getPostId());
        postDtoResponse.setPostName(post.getPostName());
        postDtoResponse.setPostText(post.getPostText());
        return postDtoResponse;
    }

    public PostDtoResponseShort toPostDtoResponseShort(final Post post) {
        final PostDtoResponseShort postDtoResponseShort = new PostDtoResponseShort();
        postDtoResponseShort.setPostName(post.getPostName());
        return  postDtoResponseShort;
    }

}