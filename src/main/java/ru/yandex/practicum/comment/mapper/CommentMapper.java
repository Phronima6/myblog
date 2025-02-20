package ru.yandex.practicum.comment.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.comment.dto.CommentDtoResponse;
import ru.yandex.practicum.comment.model.Comment;

@Component
public class CommentMapper {

    public static CommentDtoResponse toCommentDtoResponse (final Comment comment) {
        final CommentDtoResponse commentDtoResponse = new CommentDtoResponse();
        commentDtoResponse.setCommentText(comment.getCommentText());
        return commentDtoResponse;
    }

}