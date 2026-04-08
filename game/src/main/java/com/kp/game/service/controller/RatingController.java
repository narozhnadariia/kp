package com.kp.game.service.controller;

import com.kp.game.entity.Rating;
import com.kp.game.service.jdbc.RatingServiceJDBC;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingServiceJDBC ratingServiceJDBC =  new RatingServiceJDBC();

    public RatingController() {
    }

    @PostMapping
    public void setRating(@RequestBody Rating rating) {
        ratingServiceJDBC.setRating(rating);
    }

    @GetMapping("/{game}")
    public int getAverageRating(@PathVariable String game) {
        return ratingServiceJDBC.getAverageRating(game);
    }

    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) {
        return ratingServiceJDBC.getRating(game, player);
    }

    @DeleteMapping
    public void reset() {
        ratingServiceJDBC.reset();
    }
}