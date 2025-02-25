package ru.yandex.practicum.image.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.yandex.practicum.image.model.Image;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.Optional;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Repository
@RequiredArgsConstructor
public class ImageRepository {

    JdbcTemplate jdbcTemplate;

    private final RowMapper<Image> imageRowMapper = (rs, rowNum) -> {
        final Image image = new Image();
        image.setImageId(rs.getLong("image_id"));
        image.setImageData(rs.getBytes("image_data"));
        image.setImageName(rs.getString("image_name"));
        return image;
    };

    public Image save(final Image image) {
        final String sql = """
        INSERT INTO images (image_data, image_name)
        VALUES (?, ?)
        """;
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"image_id"});
            ps.setBytes(1, image.getImageData());
            ps.setString(2, image.getImageName());
            return ps;
        }, keyHolder);
        final Long imageId = keyHolder.getKey().longValue();
        image.setImageId(imageId);
        return image;
    }


    public Optional<Image> findById(final Long imageId) {
        final String sql = """
            SELECT *
            FROM images
            WHERE image_id = ?
            """;
        return jdbcTemplate.query(sql, imageRowMapper, imageId).stream().findFirst();
    }

    public void update(final Image image) {
        final String sql = """
            UPDATE images
            SET image_data = ?, image_name = ?
            WHERE image_id = ?
            """;
        jdbcTemplate.update(sql, image.getImageData(), image.getImageName(), image.getImageId());
    }

}