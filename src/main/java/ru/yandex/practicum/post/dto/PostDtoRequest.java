package ru.yandex.practicum.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDtoRequest {

    @NotBlank(message = "Имя поста не может быть пустым.")
    @Size(min = 6, max = 254, message = "Имя поста должно быть не менее 6 и не более 254 символов.")
    String postName;
    @NotNull(message = "Пост должен содержать картинку.")
    MultipartFile multipartFile;
    @NotBlank(message = "Текст поста не может быть пустым.")
    @Size(min = 6, max = 8999, message = "Текст поста должен быть не менее 6 и не более 8999 символов.")
    String postText;
    String tagsText;

}