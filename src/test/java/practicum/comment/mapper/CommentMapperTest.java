package practicum.comment.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.comment.dto.CommentDtoResponse;
import ru.yandex.practicum.comment.mapper.CommentMapper;
import ru.yandex.practicum.comment.model.Comment;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations = "classpath:test-application.properties")
public class CommentMapperTest {

    @Test
    void toCommentDtoResponse() {
        final Comment comment = new Comment();
        comment.setCommentText("Test Comment");
        final CommentDtoResponse commentDtoResponse = CommentMapper.toCommentDtoResponse(comment);
        assertEquals("Test Comment", commentDtoResponse.getCommentText());
    }

}