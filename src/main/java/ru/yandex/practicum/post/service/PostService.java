package ru.yandex.practicum.post.service;

import org.springframework.data.domain.Page;
import ru.yandex.practicum.post.dto.PostDtoRequest;
import ru.yandex.practicum.post.dto.PostDtoResponse;
import ru.yandex.practicum.post.dto.PostDtoResponseShort;
import ru.yandex.practicum.post.model.Post;
import java.util.List;

public interface PostService {

    void savePost(final PostDtoRequest postDtoRequest);

    PostDtoResponse getPostById(final Long postId);

    List<PostDtoResponseShort> getPosts();

    List<PostDtoResponseShort> getPostsWithTags(final String tagText);

    Page<PostDtoResponseShort> getPostsPaginated(final Integer size, final Integer page);

    void updatePost(final PostDtoRequest postDtoRequest, final Long postId);

    void deletePost(final Long postId);

    Post findPostByIdOrException(final Long postId);

    String getPreview(final String postText);

}