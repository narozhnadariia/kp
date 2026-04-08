package com.kp.game.config;

import com.kp.game.service.interfces.CommentService;
import com.kp.game.service.interfces.RatingService;
import com.kp.game.service.interfces.ScoreService;
import com.kp.game.service.jdbc.CommentServiceJDBC;
import com.kp.game.service.jdbc.RatingServiceJDBC;
import com.kp.game.service.jdbc.ScoreServiceJDBC;
import com.kp.game.service.jpa.CommentServiceJPA;
import com.kp.game.service.jpa.RatingServiceJPA;
import com.kp.game.service.jpa.ScoreServiceJPA;
import com.kp.game.service.restclient.CommentServiceRESTClient;
import com.kp.game.service.restclient.RatingServiceRESTClient;
import com.kp.game.service.restclient.ScoreServiceRESTClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceSelectionConfig {

    @Bean
    public ScoreService scoreService(ScoreServiceJPA scoreServiceJPA,
                                     RestTemplate restTemplate,
                                     @Value("${game.service-mode:jpa}") String mode) {
        System.out.println(mode);
        return switch (mode.toLowerCase()) {
            case "jdbc" -> new ScoreServiceJDBC();
            case "rest" -> new ScoreServiceRESTClient(restTemplate);
            default -> scoreServiceJPA;
        };
    }

    @Bean
    public CommentService commentService(CommentServiceJPA commentServiceJPA,
                                         RestTemplate restTemplate,
                                         @Value("${game.service-mode:jpa}") String mode) {
        return switch (mode.toLowerCase()) {
            case "jdbc" -> new CommentServiceJDBC();
            case "rest" -> new CommentServiceRESTClient(restTemplate);
            default -> commentServiceJPA;
        };
    }

    @Bean
    public RatingService ratingService(RatingServiceJPA ratingServiceJPA,
                                       RestTemplate restTemplate,
                                       @Value("${game.service-mode:jpa}") String mode) {
        return switch (mode.toLowerCase()) {
            case "jdbc" -> new RatingServiceJDBC();
            case "rest" -> new RatingServiceRESTClient(restTemplate);
            default -> ratingServiceJPA;
        };
    }
}