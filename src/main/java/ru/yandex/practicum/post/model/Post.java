package ru.yandex.practicum.post.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "posts")
public class Post {

    @Column(name = "post_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long postId;
    @Column(name = "image_id", nullable = false)
    Long imageId;
    @Column(name = "post_name", nullable = false)
    String postName;
    @Column(name = "post_text", nullable = false)
    String postText;

}