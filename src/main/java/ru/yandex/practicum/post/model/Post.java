package ru.yandex.practicum.post.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {

    Long postId;
    Long imageId;
    String postName;
    String postText;

}