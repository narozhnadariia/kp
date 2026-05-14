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

//    //фільтр коментарів
//    @GetMapping("/filter")
//    public List<Comment> filterComments(
//            @RequestParam String game,
//            @RequestParam(required = false) String text
//    ) {
//        List<Comment> comments = commentService.getComments(game);
//
//        if (text == null || text.isBlank()) {
//            return comments;
//        }
//
//        return comments.stream()
//                .filter(comment -> comment.getComment() != null &&
//                        comment.getComment().toLowerCase().contains(text.toLowerCase()))
//                .toList();
//    }
//    //
}