package practicum.post.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.comment.service.CommentService;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.image.model.Image;
import ru.yandex.practicum.image.service.ImageService;
import ru.yandex.practicum.like.service.LikeService;
import ru.yandex.practicum.post.dto.PostDtoRequest;
import ru.yandex.practicum.post.mapper.PostMapper;
import ru.yandex.practicum.post.model.Post;
import ru.yandex.practicum.post.repository.PostRepository;
import ru.yandex.practicum.post.service.PostServiceImplements;
import ru.yandex.practicum.tag.service.TagService;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource(locations = "classpath:test-application.properties")
class PostServiceTest {

    @Mock
    PostRepository postRepository;
    @Mock
    PostMapper postMapper;
    @Mock
    ImageService imageService;
    @Mock
    LikeService likeService;
    @Mock
    TagService tagService;
    @Mock
    CommentService commentService;
    @InjectMocks
    PostServiceImplements postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePost() {
        final PostDtoRequest postDtoRequest = new PostDtoRequest();
        final MultipartFile file = mock(MultipartFile.class);
        postDtoRequest.setMultipartFile(file);
        final Image image = new Image();
        image.setImageId(1L);
        final Post post = new Post();
        final Post savedPost = new Post();
        savedPost.setPostId(1L);
        when(imageService.saveImage(file)).thenReturn(image);
        when(postMapper.toPost(postDtoRequest)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(savedPost);
        postService.savePost(postDtoRequest);
        verify(imageService, times(1)).saveImage(file);
        verify(postMapper, times(1)).toPost(postDtoRequest);
        verify(postRepository, times(1)).save(post);
        verify(tagService, times(1)).saveTags(postDtoRequest.getTagsText(), savedPost.getPostId());
    }

    @Test
    void deletePost() {
        final Long postId = 1L;
        final Post post = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        postService.deletePost(postId);
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void findPostByIdOrException() {
        final Long postId = 1L;
        final Post post = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        final Post result = postService.findPostByIdOrException(postId);
        assertNotNull(result);
        assertEquals(post, result);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void findPostByIdOrException_throwsNotFoundException() {
        final Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            postService.findPostByIdOrException(postId);
        });
        assertEquals("Пост с id: 1 не найден.", exception.getMessage());
    }

}