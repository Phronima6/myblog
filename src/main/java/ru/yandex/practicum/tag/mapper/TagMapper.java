package ru.yandex.practicum.tag.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.tag.dto.TagDtoResponse;
import ru.yandex.practicum.tag.model.Tag;

@Component
public class TagMapper {

    public static TagDtoResponse toTagDto(final Tag tag) {
        final TagDtoResponse tagDtoResponse = new TagDtoResponse();
        tagDtoResponse.setTagText(tag.getTagText());
        return tagDtoResponse;
    }

}