package ru.yandex.practicum.comment.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.comment.service.CommentService;

@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/feed")
@RequiredArgsConstructor
public class CommentController {

    CommentService commentService;

    @PostMapping("/post/{id}/saveComment")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveComment(@PathVariable(name = "id") final Long postId,
                              @RequestParam(name = "text") final String commentText) {
        commentService.saveComment(commentText, postId);
        return "redirect:/feed/post/" + postId;
    }

    @PostMapping(value = "/post/comment")
    public String updateComment(@RequestParam(name = "id") final Long commentId,
                                @RequestParam(name = "text") final String commentText,
                                @RequestParam(name = "postId") final Long postId) {
        commentService.updateComment(commentId, commentText);
        return "redirect:/feed/post/" + postId;
    }

    @PostMapping(value = "/post/{id}/deleteComment/{commentId}", params = "_method=delete")
    public String deleteComment(@PathVariable(name = "commentId") final Long commentId,
                                @PathVariable(name = "id") final Long postId) {
        commentService.deleteComment(commentId);
        return "redirect:/feed/post/" + postId;
    }

}