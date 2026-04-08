package com.kp.game.service.restclient;

import com.kp.game.entity.Score;
import com.kp.game.service.interfces.ScoreService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


//Тобто Spring може його створити і підставити туди, де потрібен ScoreService
//@Service
public class ScoreServiceRESTClient implements ScoreService {


    //саме сюди буде звертатися клієнт
    private final String url = "http://localhost:8080/api/score";
    //це інструмент Spring для HTTP запитів, через нього можна робити GET/POST/DELETE.ВІдправляє запити серверу
    private final RestTemplate restTemplate;


    //Сюди Spring підставляє готовий RestTemplate, який я створила в RestClientConfig ,тут Spring дає його як залежність.
    public ScoreServiceRESTClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    //ідправляє HTTP POST запит на url мій і передає обєкт скор
    @Override
    public void addScore(Score score) {
        //куди відправляємо , які данні і не очікую нічого в відповідь
        restTemplate.postForEntity(url, score, Void.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
        //Це метод для GET-запиту, який очікує отримати відповідь і перетворити її в Java-об’єкт.
        Score[] scores = restTemplate.getForObject(url + "/" + game, Score[].class);
        return scores == null ? List.of() : Arrays.asList(scores);
    }

    @Override
    public void reset() {
        restTemplate.delete(url);
    }
}