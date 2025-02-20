package ru.yandex.practicum.image.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "images")
public class Image {

    @Column(name = "image_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long imageId;
    @Column(name = "image_data", nullable = false)
    @Lob
    byte[] imageData;
    @Column(name = "image_name", nullable = false)
    String imageName;

}