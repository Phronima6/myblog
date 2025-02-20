package ru.yandex.practicum.like.service;

public interface LikeService {

    void saveLike(final Long postId);

    Integer getCountLikesOfPost(final Long postId);

}