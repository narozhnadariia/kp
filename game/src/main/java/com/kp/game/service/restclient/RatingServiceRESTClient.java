package com.kp.game.service.restclient;

import com.kp.game.entity.Rating;
import com.kp.game.service.interfces.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Service
public class RatingServiceRESTClient implements RatingService {

    private final String url = "http://localhost:8080/api/rating";
    private final RestTemplate restTemplate;

    public RatingServiceRESTClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
        Integer result = restTemplate.getForObject(url + "/" + game, Integer.class);
        return result == null ? 0 : result;
    }

    @Override
    public int getRating(String game, String player) {
        Integer result = restTemplate.getForObject(url + "/" + game + "/" + player, Integer.class);
        return result == null ? 0 : result;
    }

    @Override
    public void reset() {
        restTemplate.delete(url);
    }
}