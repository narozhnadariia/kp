package com.kp.game.service.interfces;

import com.kp.game.entity.Rating;

public interface RatingService {
    void setRating(Rating rating);
    int getAverageRating(String game);
    int getRating(String game, String player);
    void reset();
}