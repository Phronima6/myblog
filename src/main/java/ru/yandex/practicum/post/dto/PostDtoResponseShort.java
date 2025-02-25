package ru.yandex.practicum.post.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.tag.dto.TagDtoResponse;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDtoResponseShort {

    Long postId;
    String postName;
    String base64Image;
    String postPreview;
    Integer countLikes;
    List<TagDtoResponse> tagsDto;
    Integer countComments;

}