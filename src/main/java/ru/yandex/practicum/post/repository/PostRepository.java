package ru.yandex.practicum.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.post.model.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPostIdIn(final List<Long> postIds);

}