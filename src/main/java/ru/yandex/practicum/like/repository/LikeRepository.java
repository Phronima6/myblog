package ru.yandex.practicum.like.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.like.model.Like;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Repository
@RequiredArgsConstructor
public class LikeRepository {

    JdbcTemplate jdbcTemplate;

    private final RowMapper<Like> likeRowMapper = (rs, rowNum) -> {
        final Like like = new Like();
        like.setLikeId(rs.getLong("like_id"));
        like.setPostId(rs.getLong("post_id"));
        return like;
    };

    public Like save(final Like like) {
        final String sql = """
            INSERT INTO likes (post_id)
            VALUES (?)
            """;
        jdbcTemplate.update(sql, like.getPostId());
        return like;
    }

    public Integer countByPostId(final Long postId) {
        final String sql = """
            SELECT COUNT(*)
            FROM likes
            WHERE post_id = ?
            """;
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }

}