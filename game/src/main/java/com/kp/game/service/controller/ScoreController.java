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
    //додає коментарі
    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreServiceJDBC.addScore(score);
    }
    //отримати коментар гри
    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreServiceJDBC.getTopScores(game);
    }


    //очистити коментарі
    @DeleteMapping
    public void reset() {
        scoreServiceJDBC.reset();
    }

}

//лідерборд
//package com.kp.game.service.controller;
//
//import com.kp.game.entity.Score;
//import com.kp.game.service.jdbc.ScoreServiceJDBC;
//import org.springframework.web.bind.annotation.*;
//
//        import java.util.List;
//
//@RestController
//@RequestMapping("/api/score")
//@CrossOrigin(origins = "*")
//public class ScoreController {
//
//    private final ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
//
//    @PostMapping
//    public void addScore(@RequestBody Score score) {
//        scoreServiceJDBC.addScore(score);
//    }
//
//    @GetMapping("/{game}")
//    public List<Score> getTopScores(@PathVariable String game) {
//        return scoreServiceJDBC.getTopScores(game);
//    }
//
//    @GetMapping("/leaderboard")
//    public List<Score> getLeaderboard() {
//        return scoreServiceJDBC.getTopScores("Slide-A-Lama");
//    }
//
//    @DeleteMapping
//    public void reset() {
//        scoreServiceJDBC.reset();
//    }
//}