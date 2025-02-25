package practicum.comment.mapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.MyblogApplication;
import ru.yandex.practicum.comment.dto.CommentDtoResponse;
import ru.yandex.practicum.comment.mapper.CommentMapper;
import ru.yandex.practicum.comment.model.Comment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(classes = MyblogApplication.class)
@TestPropertySource(locations = "classpath:test-application.properties")
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void toCommentDtoResponse() {
        final Comment comment = new Comment();
        comment.setCommentText("Test Comment");
        final CommentDtoResponse commentDtoResponse = commentMapper.toCommentDtoResponse(comment);
        assertEquals("Test Comment", commentDtoResponse.getCommentText());
    }

}