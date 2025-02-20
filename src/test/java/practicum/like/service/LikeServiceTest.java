package practicum.like.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.like.model.Like;
import ru.yandex.practicum.like.repository.LikeRepository;
import ru.yandex.practicum.like.service.LikeServiceImplements;
import ru.yandex.practicum.post.service.PostServiceImplements;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource(locations = "classpath:test-application.properties")
class LikeServiceTest {

    @Mock
    LikeRepository likeRepository;
    @Mock
    PostServiceImplements postService;
    @InjectMocks
    LikeServiceImplements likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveLike() {
        final Long postId = 1L;
        final Like savedLike = new Like();
        savedLike.setLikeId(1L);
        when(likeRepository.save(any(Like.class))).thenReturn(savedLike);
        likeService.saveLike(postId);
        verify(postService, times(1)).findPostByIdOrException(postId);
        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void getCountLikesOfPost() {
        final Long postId = 1L;
        final Integer count = 5;
        when(likeRepository.countByPostId(postId)).thenReturn(count);
        Integer result = likeService.getCountLikesOfPost(postId);
        assertEquals(count, result);
        verify(postService, times(1)).findPostByIdOrException(postId);
    }

}