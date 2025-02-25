package ru.yandex.practicum.like.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Like {

    Long likeId;
    Long postId;

}