package com.kp.game.service.controller;

import com.kp.game.entity.Comment;
import com.kp.game.service.interfces.CommentService;
import com.kp.game.service.jdbc.CommentServiceJDBC;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService = new CommentServiceJDBC();

    public CommentController() {
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        return commentService.getComments(game);
    }

    @DeleteMapping
    public void reset() {
        commentService.reset();
    }
}