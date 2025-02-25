package ru.yandex.practicum.tag.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tag {

    Long tagId;
    String tagText;
    Long postId;

}