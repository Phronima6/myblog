package practicum.like.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.like.controller.LikeController;
import ru.yandex.practicum.like.service.LikeService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource(locations = "classpath:test-application.properties")
class LikeControllerTest {

    @Mock
    LikeService likeService;
    LikeController likeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        likeController = new LikeController(likeService);
    }

    @Test
    void saveLike() {
        final Long postId = 1L;
        final String expectedRedirect = "redirect:/feed/post/" + postId;
        final String result = likeController.saveLike(postId);
        verify(likeService, times(1)).saveLike(postId);
        assertEquals(expectedRedirect, result);
    }

}