package ru.yandex.practicum.comment.service;

import ru.yandex.practicum.comment.dto.CommentDtoResponse;
import java.util.List;

public interface CommentService {

    void saveComment(final String commentText, final Long postId);

    List<CommentDtoResponse> findAllCommentsByPostId(final Long postId);

    Integer getCountCommentsOfPost(final Long postId);

    void updateComment(final Long commentId, final String commentText);

    void deleteComment(final Long commentId);

}