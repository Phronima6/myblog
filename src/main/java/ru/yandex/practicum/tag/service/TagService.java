package ru.yandex.practicum.tag.service;

import ru.yandex.practicum.tag.dto.TagDtoResponse;
import java.util.List;

public interface TagService {

    void saveTags(String tagsText, final Long postId);

    List<TagDtoResponse> findAllTagsByPostId(final Long postId);

    List<Long> findAllPostIdByTagText(final String tagText);

    void updateTags(final String tagsText, final Long postId);

}