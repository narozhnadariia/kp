package com.kp.game.service.controller;

import com.kp.game.entity.Score;
import com.kp.game.service.jdbc.ScoreServiceJDBC;
import com.kp.game.service.jpa.ScoreServiceJPA;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    private final ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();

    public ScoreController() {

    }

    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreServiceJDBC.addScore(score);
    }

    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreServiceJDBC.getTopScores(game);
    }

    @DeleteMapping
    public void reset() {
        scoreServiceJDBC.reset();
    }
}