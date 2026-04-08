package com.kp.game.service.restclient;

import com.kp.game.entity.Comment;
import com.kp.game.service.interfces.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

//@Service
public class CommentServiceRESTClient implements CommentService {

    private final String url = "http://localhost:8080/api/comment";
    private final RestTemplate restTemplate;

    public CommentServiceRESTClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void addComment(Comment comment) {
        restTemplate.postForEntity(url, comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) {
        Comment[] comments = restTemplate.getForObject(url + "/" + game, Comment[].class);
        return comments == null ? List.of() : Arrays.asList(comments);
    }

    @Override
    public void reset() {
        restTemplate.delete(url);
    }
}