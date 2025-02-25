package practicum.tag.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.MyblogApplication;
import ru.yandex.practicum.tag.model.Tag;
import ru.yandex.practicum.tag.repository.TagRepository;
import ru.yandex.practicum.tag.service.TagServiceImplements;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest(classes = MyblogApplication.class)
@TestPropertySource(locations = "classpath:test-application.properties")
class TagServiceTest {

    @Mock
    TagRepository tagRepository;
    @InjectMocks
    TagServiceImplements tagService;

    @Test
    void findAllPostIdByTagText() {
        final String tagText = "testTag";
        final Tag tag = new Tag();
        tag.setPostId(1L);
        final List<Tag> tags = List.of(tag);
        when(tagRepository.findByTagText(tagText)).thenReturn(tags);
        final List<Long> result = tagService.findAllPostIdByTagText(tagText);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0));
        verify(tagRepository, times(1)).findByTagText(tagText);
    }

}