package practicum.comment.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.comment.dto.CommentDtoResponse;
import ru.yandex.practicum.comment.model.Comment;
import ru.yandex.practicum.comment.repository.CommentRepository;
import ru.yandex.practicum.comment.service.CommentServiceImplements;
import ru.yandex.practicum.post.service.PostService;
import ru.yandex.practicum.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource(locations = "classpath:test-application.properties")
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    PostService postService;
    @InjectMocks
    CommentServiceImplements commentService;

    @Test
    void saveComment() {
        final Long postId = 1L;
        final String commentText = "Test Comment";
        final Comment savedComment = new Comment();
        savedComment.setCommentId(1L);
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);
        commentService.saveComment(commentText, postId);
        verify(postService, times(1)).findPostByIdOrException(postId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void findAllCommentsByPostId() {
        final Long postId = 1L;
        final Comment comment = new Comment();
        comment.setCommentText("Test Comment");
        when(commentRepository.findByPostId(postId)).thenReturn(List.of(comment));
        final List<CommentDtoResponse> commentDtoResponses = commentService.findAllCommentsByPostId(postId);
        assertNotNull(commentDtoResponses);
        assertEquals(1, commentDtoResponses.size());
        assertEquals("Test Comment", commentDtoResponses.get(0).getCommentText());
    }

    @Test
    void getCountCommentsOfPost() {
        final Long postId = 1L;
        final Integer count = 5;
        when(commentRepository.countByPostId(postId)).thenReturn(count);
        final Integer result = commentService.getCountCommentsOfPost(postId);
        assertEquals(count, result);
    }

    @Test
    void updateComment() {
        final Long commentId = 1L;
        final String commentText = "Updated Comment";
        final Comment comment = new Comment();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        commentService.updateComment(commentId, commentText);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(comment);
        assertEquals(commentText, comment.getCommentText());
    }

    @Test
    void updateComment_ThrowsNotFoundException() {
        final Long commentId = 1L;
        final String commentText = "Updated Comment";
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> commentService.updateComment(commentId, commentText));
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, never()).save(any());
    }

    @Test
    void deleteComment() {
        final Long commentId = 1L;
        commentService.deleteComment(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void deleteComment_WhenCommentDoesNotExist() {
        final Long commentId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(commentRepository).deleteById(commentId);
        assertThrows(EmptyResultDataAccessException.class, () -> commentService.deleteComment(commentId));
        verify(commentRepository, times(1)).deleteById(commentId);
    }

}