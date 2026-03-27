package com.kp.game.service.interfces;

import com.kp.game.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getComments(String game);
    void reset();
}