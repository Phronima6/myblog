package ru.yandex.practicum.comment.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.comment.dto.CommentDtoResponse;
import ru.yandex.practicum.comment.mapper.CommentMapper;
import ru.yandex.practicum.comment.model.Comment;
import ru.yandex.practicum.comment.repository.CommentRepository;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.post.service.PostService;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@Slf4j
@Transactional
public class CommentServiceImplements implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    PostService postService;

    @Override
    public void saveComment(final String commentText, final Long postId) {
        log.info("Попытка загрузить комментарий для поста с id: {}.", postId);
        postService.findPostByIdOrException(postId);
        final Comment comment = new Comment();
        comment.setCommentText(commentText);
        comment.setPostId(postId);
        final Comment savedComment = commentRepository.save(comment);
        log.info("Загружен комментарий с id: {}.", savedComment.getCommentId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDtoResponse> findAllCommentsByPostId(final Long postId) {
        log.info("Попытка получить комментарий для поста с id: {}.", postId);
        return commentRepository.findByPostId(postId).stream()
                .map(CommentMapper::toCommentDtoResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCountCommentsOfPost(final Long postId) {
        log.info("Попытка получить количество комментариев к посту с id: {}.", postId);
        return commentRepository.countByPostId(postId);
    }

    @Override
    public void updateComment(final Long commentId, final String commentText) {
        log.info("Попытка обновить комментарий с id: {}", commentId);
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.error("Комментарий с id: {} не найден", commentId);
                    return new NotFoundException("Комментарий не найден");
                });
        comment.setCommentText(commentText);
        commentRepository.save(comment);
        log.info("Комментарий с id: {} успешно обновлен", commentId);
    }

    @Override
    public void deleteComment(final Long commentId) {
        log.info("Попытка удалить комментарий с id: {}", commentId);
        commentRepository.deleteById(commentId);
        log.info("Комментарий с id: {} успешно удален", commentId);
    }

}