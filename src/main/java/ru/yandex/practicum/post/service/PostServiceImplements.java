package ru.yandex.practicum.post.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.comment.service.CommentService;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.image.model.Image;
import ru.yandex.practicum.image.service.ImageService;
import ru.yandex.practicum.like.service.LikeService;
import ru.yandex.practicum.post.dto.PostDtoRequest;
import ru.yandex.practicum.post.dto.PostDtoResponse;
import ru.yandex.practicum.post.dto.PostDtoResponseShort;
import ru.yandex.practicum.post.mapper.PostMapper;
import ru.yandex.practicum.post.model.Post;
import ru.yandex.practicum.post.repository.PostRepository;
import ru.yandex.practicum.tag.service.TagService;
import java.util.Base64;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
@Slf4j
@Transactional
public class PostServiceImplements implements PostService {

    PostRepository postRepository;
    PostMapper postMapper;
    ImageService imageService;
    LikeService likeService;
    TagService tagService;
    CommentService commentService;
    static Integer PREVIEW_MAX_LENGTH = 5;

    public PostServiceImplements(final PostRepository postRepository, final PostMapper postMapper,
                                 final ImageService imageService, @Lazy final LikeService likeService,
                                 final TagService tagService, @Lazy final CommentService commentService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.imageService = imageService;
        this.likeService = likeService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    @Override
    public void savePost(final PostDtoRequest postDtoRequest) {
        log.info("Попытка загрузить пост.");
        final Image image = imageService.saveImage(postDtoRequest.getMultipartFile());
        final Post post = postMapper.toPost(postDtoRequest);
        post.setImageId(image.getImageId());
        final Post savedPost = postRepository.save(post);
        final Long savedPostId = savedPost.getPostId();
        tagService.saveTags(postDtoRequest.getTagsText(), savedPostId);
        log.info("Пост с id: {} загружен.", savedPostId);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDtoResponse getPostById(final Long postId) {
        log.info("Попытка найти пост с id: {}.", postId);
        final Post post = findPostByIdOrException(postId);
        final PostDtoResponse postDtoResponse = postMapper.toPostDtoResponse(post);
        final Image image = imageService.getImage(post.getImageId());
        final String imageBase64 = Base64.getEncoder().encodeToString(image.getImageData());
        postDtoResponse.setBase64Image(imageBase64);
        postDtoResponse.setPostPreview(getPreview(post.getPostText()));
        postDtoResponse.setCountLikes(likeService.getCountLikesOfPost(postId));
        postDtoResponse.setTagsDto(tagService.findAllTagsByPostId(postId));
        postDtoResponse.setCommentsDtoResponse(commentService.findAllCommentsByPostId(postId));
        log.info("Пост с id: {} найден.", postDtoResponse.getPostId());
        return postDtoResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDtoResponseShort> getPosts() {
        log.info("Попытка найти все посты.");
        return postRepository.findAll().stream()
                .map(this::buildPostDtoResponseShort)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDtoResponseShort> getPostsWithTags(final String tagText) {
        log.info("Попытка найти все посты по тегу: {}.", tagText);
        final List<Long> postIds = tagService.findAllPostIdByTagText(tagText);
        log.info("По тегу найдены посты со следующими id: {}.", postIds);
        return postRepository.findByPostIdIn(postIds).stream()
                .map(this::buildPostDtoResponseShort)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDtoResponseShort> getPostsPaginated(final Integer size, final Integer page) {
        log.info("Попытка найти посты c ограничением по размеру: {}.", size);
        List<Post> posts = postRepository.findPaginated(page, size);
        int totalPosts = postRepository.countPosts();
        List<PostDtoResponseShort> postDtos = posts.stream()
                .map(this::buildPostDtoResponseShort)
                .toList();
        Pageable pageable = PageRequest.of(page - 1, size);
        return new PageImpl<>(postDtos, pageable, totalPosts);
    }

    @Override
    public void updatePost(final PostDtoRequest postDtoRequest, final Long postId) {
        log.info("Попытка обновить пост с id: {}.", postId);
        final Post oldPost = findPostByIdOrException(postId);
        final Image image = imageService.updateImage(oldPost.getImageId(), postDtoRequest.getMultipartFile());
        final Post post = postMapper.toPost(postDtoRequest);
        post.setPostId(postId);
        post.setImageId(image.getImageId());
        postRepository.update(post);
        tagService.updateTags(postDtoRequest.getTagsText(), postId);
        log.info("Пост с id: {} обновлён.", postId);
    }

    @Override
    public void deletePost(final Long postId) {
        log.info("Попытка удалить пост с id: {}.", postId);
        postRepository.deleteById(postId);
        log.info("Пост с id: {} удалён.", postId);
    }

    @Override
    @Transactional(readOnly = true)
    public Post findPostByIdOrException(final Long postId) {
        log.info("Поиск поста по id: {}.", postId);
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Пост с id: " + postId + " не найден."));
    }

    @Override
    @Transactional(readOnly = true)
    public String getPreview(final String postText) {
        log.info("Подготовка превью поста по тексту: {}.", postText);
        if (postText.isEmpty()) return "";
        int newlinePos = postText.indexOf("\n");
        int previewEnd = Math.min((newlinePos != -1 ? newlinePos + 1 : postText.length()), PREVIEW_MAX_LENGTH);
        return postText.substring(0, Math.min(previewEnd, postText.length()));
    }

    private PostDtoResponseShort buildPostDtoResponseShort(final Post post) {
        final PostDtoResponseShort postDtoResponseShort = postMapper.toPostDtoResponseShort(post);
        final Image image = imageService.getImage(post.getImageId());
        final String imageBase64 = Base64.getEncoder().encodeToString(image.getImageData());
        postDtoResponseShort.setBase64Image(imageBase64);
        postDtoResponseShort.setPostPreview(getPreview(post.getPostText()));
        postDtoResponseShort.setCountLikes(likeService.getCountLikesOfPost(post.getPostId()));
        postDtoResponseShort.setTagsDto(tagService.findAllTagsByPostId(post.getPostId()));
        postDtoResponseShort.setCountComments(commentService.getCountCommentsOfPost(post.getPostId()));
        return postDtoResponseShort;
    }

}