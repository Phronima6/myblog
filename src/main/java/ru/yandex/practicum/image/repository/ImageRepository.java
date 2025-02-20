package ru.yandex.practicum.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.image.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}