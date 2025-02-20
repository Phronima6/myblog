package ru.yandex.practicum.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.tag.model.Tag;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByPostId(final Long postId);

    List<Tag> findByTagText(final String tagText);

}