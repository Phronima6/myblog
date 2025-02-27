package practicum.tag.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.MyblogApplication;
import ru.yandex.practicum.tag.dto.TagDtoResponse;
import ru.yandex.practicum.tag.mapper.TagMapper;
import ru.yandex.practicum.tag.model.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = MyblogApplication.class)
@TestPropertySource(locations = "classpath:test-application.properties")
class TagMapperTest {

    @Test
    void toTagDto() {
        final Tag tag = new Tag();
        tag.setTagText("testTag");
        final TagDtoResponse tagDtoResponse = TagMapper.toTagDto(tag);
        assertNotNull(tagDtoResponse);
        assertEquals("testTag", tagDtoResponse.getTagText());
    }

}