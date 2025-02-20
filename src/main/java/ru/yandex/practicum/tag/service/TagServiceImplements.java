package ru.yandex.practicum.tag.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.tag.dto.TagDtoResponse;
import ru.yandex.practicum.tag.mapper.TagMapper;
import ru.yandex.practicum.tag.model.Tag;
import ru.yandex.practicum.tag.repository.TagRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@Slf4j
@Transactional
public class TagServiceImplements implements TagService {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagMapper tagMapper;

    @Override
    public void saveTags(final String tagsText, final Long postId) {
        log.info("Попытка загрузить теги для поста с id: {}.", postId);
        final Set<String> tagsSet = Arrays.stream(tagsText.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
        final List<Tag> tagsList = tagsSet.stream()
                .map(tagText -> {
                    Tag tag = new Tag();
                    tag.setTagText(tagText);
                    tag.setPostId(postId);
                    return tag;
                })
                .toList();
        final List<Tag> savedTags = tagRepository.saveAll(tagsList);
        savedTags.forEach(tag ->
                log.info("Тег c id: {} загружен для поста {}", tag.getTagId(), postId)
        );
        log.info("Теги загружены.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDtoResponse> findAllTagsByPostId(final Long postId) {
        log.info("Попытка получить все теги для поста с id: {}.", postId);
        return tagRepository.findByPostId(postId).stream()
                .map(TagMapper::toTagDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findAllPostIdByTagText(final String tagText) {
        log.info("Попытка получить все id поста для тега: {}.", tagText);
        return tagRepository.findByTagText(tagText).stream()
                .map(Tag::getPostId)
                .toList();
    }

}