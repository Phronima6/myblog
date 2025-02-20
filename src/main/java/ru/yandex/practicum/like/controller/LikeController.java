package ru.yandex.practicum.like.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.like.service.LikeService;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/feed")
public class LikeController {

    @Autowired
    LikeService likeService;

    @PostMapping("/post/{id}/addLike")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveLike(@PathVariable(name = "id") final Long postId) {
        likeService.saveLike(postId);
        return "redirect:/feed/post/" + postId;
    }

}