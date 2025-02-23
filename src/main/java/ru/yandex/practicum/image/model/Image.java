package ru.yandex.practicum.image.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {

    Long imageId;
    byte[] imageData;
    String imageName;

}