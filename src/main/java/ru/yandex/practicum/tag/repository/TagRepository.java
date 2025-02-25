package ru.yandex.practicum.tag.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.tag.model.Tag;
import java.util.List;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Repository
@RequiredArgsConstructor
public class TagRepository {

    JdbcTemplate jdbcTemplate;

    private final RowMapper<Tag> tagRowMapper = (rs, rowNum) -> {
        final Tag tag = new Tag();
        tag.setTagId(rs.getLong("tag_id"));
        tag.setTagText(rs.getString("tag_text"));
        tag.setPostId(rs.getLong("post_id"));
        return tag;
    };

    public void saveAll(final List<Tag> tags) {
        final String sql = """
            INSERT INTO tags (tag_text, post_id)
            VALUES (?, ?)
            """;
        tags.forEach(tag ->
                jdbcTemplate.update(sql, tag.getTagText(), tag.getPostId())
        );
    }

    public List<Tag> findByPostId(final Long postId) {
        final String sql = """
            SELECT *
            FROM tags
            WHERE post_id = ?
            """;
        return jdbcTemplate.query(sql, tagRowMapper, postId);
    }

    public List<Tag> findByTagText(final String tagText) {
        final String sql = """
            SELECT *
            FROM tags
            WHERE tag_text = ?
            """;
        return jdbcTemplate.query(sql, tagRowMapper, tagText);
    }

    public void deleteByPostIdAndTagTextIn(final Long postId, final Set<String> tagTexts) {
        final String sql = """
            DELETE FROM tags
            WHERE post_id = ? AND tag_text = ?
            """;
        tagTexts.forEach(tagText ->
                jdbcTemplate.update(sql, postId, tagText)
        );
    }

}