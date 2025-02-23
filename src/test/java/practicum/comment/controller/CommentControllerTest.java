package practicum.comment.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.comment.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.comment.controller.CommentController;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource(locations = "classpath:test-application.properties")
class CommentControllerTest {

    @Mock
    CommentService commentService;
    CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentController = new CommentController(commentService);
    }

    @Test
    void saveComment() {
        final Long postId = 1L;
        final String commentText = "Test Comment";
        final String expectedRedirect = "redirect:/feed/post/" + postId;
        final String result = commentController.saveComment(postId, commentText);
        verify(commentService, times(1)).saveComment(commentText, postId);
        assertEquals(expectedRedirect, result);
    }

    @Test
    void updateComment() {
        final Long commentId = 1L;
        final String commentText = "Updated Comment";
        final Long postId = 1L;
        final String expectedRedirect = "redirect:/feed/post/" + postId;
        final String result = commentController.updateComment(commentId, commentText, postId);
        verify(commentService, times(1)).updateComment(commentId, commentText);
        assertEquals(expectedRedirect, result);
    }

    @Test
    void deleteComment() {
        final Long commentId = 1L;
        final Long postId = 1L;
        final String expectedRedirect = "redirect:/feed/post/" + postId;
        final String result = commentController.deleteComment(commentId, postId);
        verify(commentService, times(1)).deleteComment(commentId);
        assertEquals(expectedRedirect, result);
    }

}