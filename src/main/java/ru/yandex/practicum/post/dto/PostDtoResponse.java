package ru.yandex.practicum.post.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.comment.dto.CommentDtoResponse;
import ru.yandex.practicum.tag.dto.TagDtoResponse;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"postId"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDtoResponse {

    Long postId;
    String postName;
    String postText;
    String base64Image;
    String postPreview;
    Integer countLikes;
    List<TagDtoResponse> tagsDto;
    List<CommentDtoResponse> commentsDtoResponse;

}