package practicum.image;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.exception.ImageProcessingException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.image.model.Image;
import ru.yandex.practicum.image.repository.ImageRepository;
import ru.yandex.practicum.image.service.ImageServiceImplements;
import java.io.IOException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TestPropertySource(locations = "classpath:test-application.properties")
public class ImageServiceTest {

    @Mock
    ImageRepository imageRepository;
    @Mock
    MultipartFile file;
    ImageServiceImplements imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageService = new ImageServiceImplements(imageRepository);
    }

    @Test
    void saveImage() throws IOException {
        final String fileName = "testImage.jpg";
        byte[] fileContent = "test content".getBytes();
        final Image image = new Image();
        image.setImageName(fileName);
        image.setImageData(fileContent);
        final Image savedImage = new Image();
        savedImage.setImageId(1L);
        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getBytes()).thenReturn(fileContent);
        when(imageRepository.save(any(Image.class))).thenReturn(savedImage);
        final Image result = imageService.saveImage(file);
        assertEquals(savedImage, result);
        verify(imageRepository, times(1)).save(any(Image.class));
    }

    @Test
    void getImage() {
        final Long imageId = 1L;
        final Image image = new Image();
        image.setImageId(imageId);
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));
        final Image result = imageService.getImage(imageId);
        assertEquals(image, result);
        verify(imageRepository, times(1)).findById(imageId);
    }

    @Test
    void saveImage_throwsImageProcessingException() throws IOException {
        final String fileName = "testImage.jpg";
        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getBytes()).thenThrow(new IOException("Test Exception"));
        ImageProcessingException exception = assertThrows(ImageProcessingException.class, () -> {
            imageService.saveImage(file);
        });
        assertEquals("Не удалось обработать изображение.", exception.getMessage());
    }

    @Test
    void getImage_throwsNotFoundException() {
        final Long imageId = 1L;
        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            imageService.getImage(imageId);
        });
        assertEquals("Изображение не найдено.", exception.getMessage());
    }

}