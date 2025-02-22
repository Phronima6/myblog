package ru.yandex.practicum.comment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDtoResponse {

    Long commentId;
    String commentText;

}