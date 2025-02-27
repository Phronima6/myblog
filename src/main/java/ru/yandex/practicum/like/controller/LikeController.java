package ru.yandex.practicum.like.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.like.service.LikeService;

@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/feed")
@RequiredArgsConstructor
public class LikeController {

    LikeService likeService;

    @PostMapping("/post/{id}/addLike")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveLike(@PathVariable(name = "id") final Long postId) {
        likeService.saveLike(postId);
        return "redirect:/feed/post/" + postId;
    }

}