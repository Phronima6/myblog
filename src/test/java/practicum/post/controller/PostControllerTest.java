package practicum.post.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.ui.Model;
import ru.yandex.practicum.post.controller.PostController;
import ru.yandex.practicum.post.dto.PostDtoRequest;
import ru.yandex.practicum.post.dto.PostDtoResponse;
import ru.yandex.practicum.post.dto.PostDtoResponseShort;
import ru.yandex.practicum.post.service.PostService;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource(locations = "classpath:test-application.properties")
class PostControllerTest {

    @Mock
    PostService postService;
    @Mock
    Model model;
    @InjectMocks
    PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePost() {
        final PostDtoRequest postDtoRequest = new PostDtoRequest();
        final String result = postController.savePost(postDtoRequest);
        verify(postService, times(1)).savePost(postDtoRequest);
        assertEquals("redirect:/feed", result);
    }

    @Test
    void getPostById() {
        final Long postId = 1L;
        final PostDtoResponse postDtoResponse = new PostDtoResponse();
        when(postService.getPostById(postId)).thenReturn(postDtoResponse);
        final String result = postController.getPostById(postId, model);
        verify(postService, times(1)).getPostById(postId);
        verify(model, times(1)).addAttribute("postDto", postDtoResponse);
        assertEquals("post", result);
    }

    @Test
    void getPosts() {
        final List<PostDtoResponseShort> feed = List.of(new PostDtoResponseShort());
        when(postService.getPosts()).thenReturn(feed);
        final String result = postController.getPosts(model);
        verify(postService, times(1)).getPosts();
        verify(model, times(1)).addAttribute("feed", feed);
        assertEquals("feed", result);
    }

    @Test
    void getPostsWithTags() {
        final String tagText = "testTag";
        final List<PostDtoResponseShort> feedWithChosenTags = List.of(new PostDtoResponseShort());
        when(postService.getPostsWithTags(tagText)).thenReturn(feedWithChosenTags);
        final String result = postController.getPostsWithTags(tagText, model);
        verify(postService, times(1)).getPostsWithTags(tagText);
        verify(model, times(1)).addAttribute("feed", feedWithChosenTags);
        assertEquals("feed", result);
    }

    @Test
    void getPostsPaginated() {
        final Integer size = 10;
        final Integer page = 1;
        final Page<PostDtoResponseShort> postPage = mock(Page.class);
        when(postService.getPostsPaginated(size, page)).thenReturn(postPage);
        when(postPage.getContent()).thenReturn(List.of(new PostDtoResponseShort()));
        final String result = postController.getPostsPaginated(size, page, model);
        verify(postService, times(1)).getPostsPaginated(size, page);
        verify(model, times(1)).addAttribute("posts", postPage.getContent());
        verify(model, times(1)).addAttribute("pageInfo", postPage);
        assertEquals("feed", result);
    }

    @Test
    void updatePost() {
        final PostDtoRequest postDtoRequest = new PostDtoRequest();
        final Long postId = 1L;
        final String result = postController.updatePost(postDtoRequest, postId);
        verify(postService, times(1)).updatePost(postDtoRequest, postId);
        assertEquals("redirect:/feed/post/" + postId, result);
    }

    @Test
    void deletePost() {
        final Long postId = 1L;
        final String result = postController.deletePost(postId);
        verify(postService, times(1)).deletePost(postId);
        assertEquals("redirect:/feed", result);
    }

}