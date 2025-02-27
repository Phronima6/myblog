package practicum.like.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.MyblogApplication;
import ru.yandex.practicum.like.controller.LikeController;
import ru.yandex.practicum.like.service.LikeService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(classes = MyblogApplication.class)
@TestPropertySource(locations = "classpath:test-application.properties")
class LikeControllerTest {

    @Mock
    LikeService likeService;
    @InjectMocks
    LikeController likeController;

    @Test
    void saveLike() {
        final Long postId = 1L;
        final String expectedRedirect = "redirect:/feed/post/" + postId;
        final String result = likeController.saveLike(postId);
        verify(likeService, times(1)).saveLike(postId);
        assertEquals(expectedRedirect, result);
    }

}