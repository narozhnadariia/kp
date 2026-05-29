package com.kp.game.service.controller;

import com.kp.game.actor.BotDifficulty;
import com.kp.game.core.Game;
import com.kp.game.core.GameMode;
import com.kp.game.core.GameState;
import com.kp.game.entity.Comment;
import com.kp.game.entity.Rating;
import com.kp.game.entity.Score;
import com.kp.game.service.dto.*;
import com.kp.game.service.interfces.CommentService;
import com.kp.game.service.interfces.RatingService;
import com.kp.game.service.interfces.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")
public class GameController {
    private final Map<Long, Game> games = new HashMap<>();
    //крок назад
//    private final Map<Long, Game> moveLog = new HashMap<>();
    //
    private long nextId = 1;
    private final ScoreService scoreService;
    private final RatingService ratingService;

    private final CommentService commentService;


    public GameController(
            ScoreService scoreService,
            RatingService ratingService,
            CommentService commentService
    ) {
        this.scoreService = scoreService;
        this.ratingService = ratingService;
        this.commentService = commentService;
    }

    @GetMapping
    public String testGamesEndpoint() {
        return "Game API works. Use POST /api/games to create a game.";
    }

    @PostMapping
    public GameResponse createGame(@RequestBody CreateGameRequest request) {
        GameMode gameMode = request.gameMode() == null
                ? GameMode.PLAYER_VS_PLAYER
                : request.gameMode();

        String player1Name = request.player1Name() == null || request.player1Name().isBlank()
                ? "Player 1"
                : request.player1Name();

        String player2Name = request.player2Name() == null || request.player2Name().isBlank()
                ? "Player 2"
                : request.player2Name();

        BotDifficulty botDifficulty = request.botDifficulty() == null
                ? BotDifficulty.EASY
                : request.botDifficulty();

        Game game = new Game(
                gameMode,
                player1Name,
                player2Name,
                botDifficulty
        );

        long id = nextId++;
        games.put(id, game);

        System.out.println("Game ID: " + id);

        return toResponse(id, game);
    }

    @PostMapping("/feedback")
    public ResponseEntity<Void> saveFeedback(@RequestBody FeedbackRequest request) {
        String gameName = "Slide-A-Lama";

        scoreService.addScore(new Score(
                request.player1Name(),
                gameName,
                request.player1Score(),
                new Date()
        ));

        scoreService.addScore(new Score(
                request.player2Name(),
                gameName,
                request.player2Score(),
                new Date()
        ));

        ratingService.setRating(new Rating(
                request.winner(),
                gameName,
                request.rating(),
                new Date()
        ));

        if (request.comment() != null && !request.comment().isBlank()) {
            commentService.addComment(new Comment(
                    request.winner(),
                    gameName,
                    request.comment(),
                    new Date()
            ));
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public GameResponse getGame(@PathVariable Long id) {
        Game game = getGameById(id);
        return toResponse(id, game);
    }

    @PostMapping("/{id}/move")
    public GameResponse makeMove(@PathVariable Long id, @RequestBody MoveRequest request) {
        Game game = getGameById(id);
        System.out.println("Game: " + game);
        if (!game.isGameOver()) {
            game.processMove(request.direction(), request.index());
        }

        return toResponse(id, game);
    }

    // bottom row bananas
    @PostMapping("/{id}/bottom-row-bananas")
    public GameResponse makeBottomRowBananas(@PathVariable Long id) {
        Game game = getGameById(id);

        if (!game.isGameOver()) {
            game.makeBottomRowBananas();
        }

        return toResponse(id, game);
    }
//





    //крок назад
//    @PostMapping("/{id}/move")
//    public GameResponse makeMove(@PathVariable Long id, @RequestBody MoveRequest request) {
//        Game game = getGameById(id);
//
//        if (!game.isGameOver()) {
//            previousGames.put(id, game.copy());
//            game.processMove(request.direction(), request.index());
//        }
//
//        return toResponse(id, game);
//    }
//    @PostMapping("/{id}/undo")
//    public GameResponse undoMove(@PathVariable Long id) {
//        Game previousGame = previousGames.get(id);
//
//        if (previousGame == null) {
//            throw new IllegalArgumentException("No previous move available");
//        }
//
//        games.put(id, previousGame);
//        previousGames.remove(id);
//
//        return toResponse(id, previousGame);
//    }
    //

    // бомба
//    @PostMapping("/{id}/bomb")
//    public GameResponse useBomb(@PathVariable Long id) {
//        Game game = getGameById(id);
//
//        if (!game.isGameOver()) {
//            game.useBomb();
//        }
//
//        return toResponse(id, game);
//    }
    //

    // поміняти плити місцями
//    @PostMapping("/{id}/swap")
//    public GameResponse swapTiles(@PathVariable Long id, @RequestBody SwapTilesRequest request) {
//        Game game = getGameById(id);
//
//        if (!game.isGameOver()) {
//            game.swapTiles(
//                    request.row1(),
//                    request.col1(),
//                    request.row2(),
//                    request.col2()
//            );
//        }
//
//        return toResponse(id, game);
//    }
//

    private Game getGameById(Long id) {
        Game game = games.get(id);

        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }

        return game;
    }

    private GameResponse toResponse(Long id, Game game) {
        String winner = null;

        if (game.getGameState() == GameState.PLAYER1_WON) {
            winner = game.getPlayer1().getName();
        } else if (game.getGameState() == GameState.PLAYER2_WON) {
            winner = game.getPlayer2().getName();
        }

        return new GameResponse(
                id,
                game.getBoard().getGrid(),
                game.getNextTile(),
                new PlayerResponse(
                        game.getPlayer1().getName(),
                        game.getPlayer1().getScore(),
                        game.getPlayer1().getLamaCount()
                ),
                new PlayerResponse(
                        game.getPlayer2().getName(),
                        game.getPlayer2().getScore(),
                        game.getPlayer2().getLamaCount()
                ),
                game.getCurrentPlayer().getName(),
                game.getGameState(),
                game.getLastMoveScore(),
                game.getLastMoveLamasWon(),
                winner
        );
    }
}