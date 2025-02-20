package ru.yandex.practicum.comment.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Column(name = "comment_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;
    @Column(name = "comment_text", nullable = false)
    String commentText;
    @Column(name = "post_id", nullable = false)
    Long postId;

}