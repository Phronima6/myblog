package practicum.tag.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.tag.model.Tag;
import ru.yandex.practicum.tag.repository.TagRepository;
import ru.yandex.practicum.tag.service.TagServiceImplements;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource(locations = "classpath:test-application.properties")
class TagServiceTest {

    @Mock
    TagRepository tagRepository;
    @InjectMocks
    TagServiceImplements tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
    }

}