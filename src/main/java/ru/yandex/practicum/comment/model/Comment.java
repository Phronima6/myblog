package ru.yandex.practicum.comment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Comment {

    Long commentId;
    String commentText;
    Long postId;

}