package ru.yandex.practicum.post.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.yandex.practicum.post.model.Post;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Repository
@RequiredArgsConstructor
public class PostRepository {

    JdbcTemplate jdbcTemplate;

    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> {
        final Post post = new Post();
        post.setPostId(rs.getLong("post_id"));
        post.setImageId(rs.getLong("image_id"));
        post.setPostName(rs.getString("post_name"));
        post.setPostText(rs.getString("post_text"));
        return post;
    };

    public Post save(final Post post) {
        final String sql = """
        INSERT INTO posts (image_id, post_name, post_text)
        VALUES (?, ?, ?)
        """;
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"post_id"});
            ps.setLong(1, post.getImageId());
            ps.setString(2, post.getPostName());
            ps.setString(3, post.getPostText());
            return ps;
        }, keyHolder);
        final Long postId = keyHolder.getKey().longValue();
        post.setPostId(postId);
        return post;
    }

    public Optional<Post> findById(final Long postId) {
        final String sql = """
            SELECT *
            FROM posts
            WHERE post_id = ?
            """;
        return jdbcTemplate.query(sql, postRowMapper, postId).stream().findFirst();
    }

    public List<Post> findAll() {
        final String sql = """
            SELECT *
            FROM posts
            """;
        return jdbcTemplate.query(sql, postRowMapper);
    }

    public List<Post> findByPostIdIn(final List<Long> postIds) {
        final String placeholders = String.join(",", Collections.nCopies(postIds.size(), "?"));
        final String sql = String.format("""
        SELECT *
        FROM posts
        WHERE post_id IN (%s)
        """, placeholders);
        return jdbcTemplate.query(sql, postRowMapper, postIds.toArray());
    }

    public List<Post> findPaginated(final Integer page, final Integer size) {
        final int offset = (page - 1) * size;
        final String sql = """
            SELECT *
            FROM posts
            ORDER BY post_id DESC
            LIMIT ? OFFSET ?
            """;
        return jdbcTemplate.query(sql, postRowMapper, size, offset);
    }

    public int countPosts() {
        final String sql = """
            SELECT COUNT(*)
            FROM posts
            """;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void update(final Post post) {
        final String sql = """
        UPDATE posts
        SET image_id = ?, post_name = ?, post_text = ?
        WHERE post_id = ?
        """;
        jdbcTemplate.update(sql,
                post.getImageId(),
                post.getPostName(),
                post.getPostText(),
                post.getPostId());
    }

    public void deleteById(final Long postId) {
        final String sql = """
            DELETE FROM posts
            WHERE post_id = ?
            """;
        jdbcTemplate.update(sql, postId);
    }

}