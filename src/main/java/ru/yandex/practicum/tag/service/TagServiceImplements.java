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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
@Slf4j
@Transactional
public class TagServiceImplements implements TagService {

    TagRepository tagRepository;

    @Autowired
    public TagServiceImplements(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

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
        tagRepository.saveAll(tagsList);
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

    @Override
    @Transactional
    public void updateTags(final String tagsText, final Long postId) {
        log.info("Попытка обновить теги для поста с id: {}.", postId);
        final Set<String> currentTagTexts = tagRepository.findByPostId(postId).stream()
                .map(Tag::getTagText)
                .collect(Collectors.toSet());
        final Set<String> newTagTexts = Arrays.stream(tagsText.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
        final Set<String> tagsToRemove = new HashSet<>(currentTagTexts);
        tagsToRemove.removeAll(newTagTexts);
        if (!tagsToRemove.isEmpty()) {
            tagRepository.deleteByPostIdAndTagTextIn(postId, tagsToRemove);
            log.info("Удалены теги: {}", tagsToRemove);
        }
        final Set<String> tagsToAdd = new HashSet<>(newTagTexts);
        tagsToAdd.removeAll(currentTagTexts);
        if (!tagsToAdd.isEmpty()) {
            final List<Tag> newTags = tagsToAdd.stream()
                    .map(tagText -> {
                        Tag tag = new Tag();
                        tag.setTagText(tagText);
                        tag.setPostId(postId);
                        return tag;
                    })
                    .toList();
            tagRepository.saveAll(newTags);
            log.info("Добавлены теги: {}", tagsToAdd);
        }
        log.info("Теги для поста с id: {} успешно обновлены.", postId);
    }

}