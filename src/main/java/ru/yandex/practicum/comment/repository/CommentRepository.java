package ru.yandex.practicum.comment.repository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.comment.model.Comment;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Repository
public class CommentRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Comment> commentRowMapper = (rs, rowNum) -> {
        final Comment comment = new Comment();
        comment.setCommentId(rs.getLong("comment_id"));
        comment.setCommentText(rs.getString("comment_text"));
        comment.setPostId(rs.getLong("post_id"));
        return comment;
    };

    public Comment save(final Comment comment) {
        final String sql = """
            INSERT INTO comments (comment_text, post_id)
            VALUES (?, ?)
            """;
        jdbcTemplate.update(sql, comment.getCommentText(), comment.getPostId());
        return comment;
    }

    public List<Comment> findByPostId(final Long postId) {
        final String sql = """
            SELECT *
            FROM comments
            WHERE post_id = ?
            """;
        return jdbcTemplate.query(sql, commentRowMapper, postId);
    }

    public Integer countByPostId(final Long postId) {
        final String sql = """
            SELECT COUNT(*)
            FROM comments
            WHERE post_id = ?
            """;
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }

    public void updateComment(final Long commentId, final String commentText) {
        final String sql = """
            UPDATE comments
            SET comment_text = ?
            WHERE comment_id = ?
            """;
        jdbcTemplate.update(sql, commentText, commentId);
    }

    public void deleteById(final Long commentId) {
        final String sql = """
            DELETE FROM comments
            WHERE comment_id = ?
            """;
        jdbcTemplate.update(sql, commentId);
    }

}