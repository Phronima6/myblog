package ru.yandex.practicum.post.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.post.dto.PostDtoRequest;
import ru.yandex.practicum.post.dto.PostDtoResponse;
import ru.yandex.practicum.post.dto.PostDtoResponseShort;
import ru.yandex.practicum.post.service.PostService;
import java.util.List;

@Controller
@RequestMapping("/feed")
public class PostController {

    PostService postService;

    @Autowired
    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String savePost(@Valid @ModelAttribute final PostDtoRequest postDtoRequest) {
        postService.savePost(postDtoRequest);
        return "redirect:/feed";
    }

    @GetMapping("/post/{id}")
    public String getPostById(@PathVariable(name = "id") final Long postId, final Model model) {
        final PostDtoResponse postDtoResponse = postService.getPostById(postId);
        model.addAttribute("postDto", postDtoResponse);
        return "post";
    }

    @GetMapping
    public String getPosts(final Model model) {
        List<PostDtoResponseShort> feed = postService.getPosts();
        model.addAttribute("feed", feed);
        return "feed";
    }

    @GetMapping("/tags")
    public String getPostsWithTags(@RequestParam(name = "tagText") final String tagText,
                                   final Model model) {
        List<PostDtoResponseShort> feedWithChosenTags = postService.getPostsWithTags(tagText);
        model.addAttribute("feed", feedWithChosenTags);
        return "feed";
    }

    @GetMapping("/feed")
    public String getPostsPaginated(@RequestParam(defaultValue = "10") final Integer size,
                                    @RequestParam(defaultValue = "1") final Integer page,
                                    final Model model) {
        Page<PostDtoResponseShort> postPage = postService.getPostsPaginated(size, page);
        model.addAttribute("feed", postPage.getContent());
        model.addAttribute("pageInfo", postPage);
        return "feed";
    }

    @PostMapping("/post/{id}/update")
    public String updatePost(@ModelAttribute final PostDtoRequest postDtoRequest,
                             @PathVariable(name = "id") final Long postId) {
        postService.updatePost(postDtoRequest, postId);
        return "redirect:/feed/post/" + postId;
    }

    @PostMapping(value = "/post/{id}", params = "_method=delete")
    public String deletePost(@PathVariable(name = "id") final Long postId) {
        postService.deletePost(postId);
        List<PostDtoResponseShort> feed = postService.getPosts();
        return "redirect:/feed";
    }

}