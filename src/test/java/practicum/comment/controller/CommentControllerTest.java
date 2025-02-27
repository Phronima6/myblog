package practicum.comment.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.MyblogApplication;
import ru.yandex.practicum.comment.service.CommentService;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.comment.controller.CommentController;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(classes = MyblogApplication.class)
@TestPropertySource(locations = "classpath:test-application.properties")
class CommentControllerTest {

    @Mock
    CommentService commentService;
    @InjectMocks
    CommentController commentController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void saveComment() throws Exception {
        final Long postId = 1L;
        final String commentText = "Test Comment";
        mockMvc.perform(MockMvcRequestBuilders.post("/feed/post/{id}/saveComment", postId)
                        .param("text", commentText))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed/post/" + postId));
        verify(commentService, times(1)).saveComment(commentText, postId);
    }

    @Test
    void updateComment() throws Exception {
        final Long commentId = 1L;
        final String commentText = "Updated Comment";
        final Long postId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/feed/post/comment")
                        .param("id", commentId.toString())
                        .param("text", commentText)
                        .param("postId", postId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed/post/" + postId));
        verify(commentService, times(1)).updateComment(commentId, commentText);
    }

    @Test
    void deleteComment() throws Exception {
        final Long commentId = 1L;
        final Long postId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/feed/post/{id}/deleteComment/{commentId}", postId, commentId)
                        .param("_method", "delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed/post/" + postId));
        verify(commentService, times(1)).deleteComment(commentId);
    }

}