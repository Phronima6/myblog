package ru.yandex.practicum.image.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.exception.ImageProcessingException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.image.model.Image;
import ru.yandex.practicum.image.repository.ImageRepository;
import java.io.IOException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ImageServiceImplements implements ImageService {

    ImageRepository imageRepository;

    @Override
    public Image saveImage(final MultipartFile file) {
        log.info("Попытка загрузить изображение.");
        final Image image = new Image();
        image.setImageName(file.getOriginalFilename());
        try {
            image.setImageData(file.getBytes());
        } catch (IOException exception) {
            log.error("Ошибка при обработке изображения: {}", exception.getMessage());
            throw new ImageProcessingException("Не удалось обработать изображение.");
        }
        final Image savedImage = imageRepository.save(image);
        log.info("Изображение успешно сохранено с ID: {}", savedImage.getImageId());
        return savedImage;
    }

    @Override
    @Transactional(readOnly = true)
    public Image getImage(final Long imageId) {
        log.info("Попытка получить изображение с id: {}", imageId);
        final Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("Изображение не найдено."));
        log.info("Изображение с id {} успешно найдено. Имя файла: {}", imageId, image.getImageName());
        return image;
    }

    @Override
    @Transactional
    public Image updateImage(final Long imageId, final MultipartFile file) {
        log.info("Попытка обновить изображение с ID: {}", imageId);
        final Image oldImage = imageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("Изображение с ID " + imageId + " не найдено."));
        oldImage.setImageName(file.getOriginalFilename());
        try {
            oldImage.setImageData(file.getBytes());
        } catch (IOException exception) {
            log.error("Ошибка при обработке изображения: {}", exception.getMessage());
            throw new ImageProcessingException("Не удалось обработать изображение.");
        }
        imageRepository.update(oldImage);
        log.info("Изображение с ID: {} успешно обновлено.", oldImage.getImageId());
        return oldImage;
    }

}