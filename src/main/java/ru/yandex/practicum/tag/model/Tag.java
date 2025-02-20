package ru.yandex.practicum.tag.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "tags")
public class Tag {

    @Column(name = "tag_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tagId;
    @Column(name = "tag_text", nullable = false)
    String tagText;
    @Column(name = "post_id", nullable = false)
    Long postId;

}