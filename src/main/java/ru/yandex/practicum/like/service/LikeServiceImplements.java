package ru.yandex.practicum.like.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.like.model.Like;
import ru.yandex.practicum.like.repository.LikeRepository;
import ru.yandex.practicum.post.service.PostService;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
@Slf4j
@Transactional
public class LikeServiceImplements implements LikeService {

    LikeRepository likeRepository;
    PostService postService;


    public LikeServiceImplements(final LikeRepository likeRepository, @Lazy final PostService postService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
    }

    @Override
    public void saveLike(final Long postId) {
        log.info("Попытка добавить лайк к посту с id: {}.", postId);
        postService.findPostByIdOrException(postId);
        final Like like = new Like(null, postId);
        final Like savedLike = likeRepository.save(like);
        log.info("Лайк c id {} добавлен к посту.", savedLike.getLikeId());
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCountLikesOfPost(final Long postId) {
        log.info("Попытка получить количество лайков к посту с id: {}.", postId);
        postService.findPostByIdOrException(postId);
        return likeRepository.countByPostId(postId);
    }

}