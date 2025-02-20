package ru.yandex.practicum.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.like.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Integer countByPostId(Long postId);

}