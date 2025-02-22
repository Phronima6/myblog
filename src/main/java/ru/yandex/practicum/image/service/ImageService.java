package ru.yandex.practicum.image.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.image.model.Image;

public interface ImageService {

    Image saveImage(final MultipartFile file);

    Image getImage(final Long imageId);

    Image updateImage(final Long imageId, final MultipartFile file);

}