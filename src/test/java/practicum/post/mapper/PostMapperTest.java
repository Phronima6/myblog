package practicum.post.mapper;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.post.dto.PostDtoRequest;
import ru.yandex.practicum.post.dto.PostDtoResponse;
import ru.yandex.practicum.post.dto.PostDtoResponseShort;
import ru.yandex.practicum.post.mapper.PostMapper;
import ru.yandex.practicum.post.model.Post;
import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@TestPropertySource(locations = "classpath:test-application.properties")
class PostMapperTest {

    PostMapper postMapper = new PostMapper();

    @Test
    void toPost() {
        final PostDtoRequest postDtoRequest = new PostDtoRequest();
        postDtoRequest.setPostName("Test Post");
        postDtoRequest.setPostText("This is a test post.");
        final Post post = postMapper.toPost(postDtoRequest);
        assertNotNull(post);
        assertEquals("Test Post", post.getPostName());
        assertEquals("This is a test post.", post.getPostText());
    }

    @Test
    void toPostDtoResponse() {
        final Post post = new Post();
        post.setPostId(1L);
        post.setPostName("Test Post");
        post.setPostText("This is a test post.");
        final PostDtoResponse postDtoResponse = postMapper.toPostDtoResponse(post);
        assertNotNull(postDtoResponse);
        assertEquals(1L, postDtoResponse.getPostId());
        assertEquals("Test Post", postDtoResponse.getPostName());
        assertEquals("This is a test post.", postDtoResponse.getPostText());
    }

    @Test
    void toPostDtoResponseShort() {
        final Post post = new Post();
        post.setPostName("Test Post");
        final PostDtoResponseShort postDtoResponseShort = postMapper.toPostDtoResponseShort(post);
        assertNotNull(postDtoResponseShort);
        assertEquals("Test Post", postDtoResponseShort.getPostName());
    }

}