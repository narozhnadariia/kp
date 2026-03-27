package com.kp.game.consoleui;

import com.kp.game.actor.*;
import com.kp.game.core.*;
import com.kp.game.entity.Comment;
import com.kp.game.entity.Rating;
import com.kp.game.entity.Score;
import com.kp.game.service.interfces.CommentService;
import com.kp.game.service.interfces.RatingService;
import com.kp.game.service.interfces.ScoreService;
import com.kp.game.service.jdbc.CommentServiceJDBC;
import com.kp.game.service.jdbc.RatingServiceJDBC;
import com.kp.game.service.jdbc.ScoreServiceJDBC;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;
import java.util.Scanner;


@Component
public class ConsoleUI {
    private final Scanner scanner;
    private Game game;
    private final ScoreService scoreService = new ScoreServiceJDBC();
    private final RatingService ratingService = new RatingServiceJDBC();
    private final CommentService comment = new CommentServiceJDBC();


    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    private void displayRules(){
        System.out.println("Rules:");
        System.out.println("- 3 same symbols win = points of tile");
        System.out.println("- 4 same symbols win = points of tile x2");
        System.out.println("- 5 same symbols win = points of tile x3");
        System.out.println("- if you score points = 70 , you take 1 lama from opponent");
        System.out.println("- winner is player who gets all 10 lamas");
        System.out.println();
        System.out.println("Tile values:");
        System.out.println("7 = 150");
        System.out.println("BAR = 100");
        System.out.println("C = 70");
        System.out.println("PE = 40");
        System.out.println("PL = 30");
        System.out.println("BA = 20");
        System.out.println("BE = 10");
        System.out.println();
    }

    private GameMode scanGameMode(){
        System.out.println("Choose game mode");
        System.out.println("1 --- Player vs PLayer");
        System.out.println("2 --- Player vs Bot");
        int choice = -1;
        while(choice < 1 || choice > 2){
            if(scanner.hasNextInt()) {
                choice = scanner.nextInt();

            }
            if(choice != 1 && choice != 2) System.out.println("Invalid input");
            scanner.nextLine();
        }
        return choice == 1 ? GameMode.PLAYER_VS_PLAYER : GameMode.PLAYER_VS_BOT;

    }

    private String scanPlayerName(String playerNumber){
        System.out.print("Enter " + playerNumber + " player name: ");
        String playerName = "";
        while (playerName.length() < 2){
            if(scanner.hasNext()) {
                playerName = scanner.next();
                scanner.nextLine();
            }
            if(playerName.length() < 2) System.out.println("Too short name!");
        }
        return playerName;
    }

    public void startGame() {
        System.out.println("----Slide_a_Lama----");
        displayRules();

        var gameMode = scanGameMode();
        var firstPLayerName = scanPlayerName("first");
        game = gameMode == GameMode.PLAYER_VS_PLAYER
                ? new Game(gameMode,firstPLayerName,scanPlayerName("second"),null)
                : new Game(gameMode,firstPLayerName,"bot",chooseBotDifficulty());

        boolean running = true;

        while (running) {
            printBoard();
            printPlayerInfo();
            System.out.println("Current tile to place: " + game.getNextTile().getSymbol());
            System.out.print("Current player: " + game.getCurrentPlayer().getName() + "\n");
            boolean botTurn =  game.getGameMode() == GameMode.PLAYER_VS_BOT && game.getCurrentPlayer() == game.getPlayer2();
            Move move;
            if (botTurn) {
                move = ((Bot) game.getPlayer2()).chooseMove(game.getBoard());
                System.out.println("Bot" + " chose " +
                        move.getDirection() + " " + move.getIndex());

            }else {
                Direction direction = chooseDirection();
                if (direction == null) {
                    System.out.print("Game over.");
                    break;
                }else {
                    move = new Move(direction,chooseIndex(direction));
                }
            }
            game.processMove(move.getDirection(), move.getIndex());
            moveResult();
            if (game.isGameOver()) {
                running = false;
            }
            if(running) {
                printBoard();
                printPlayerInfo();
                System.out.println("Next player: " + game.getCurrentPlayer().getName());
            }
        }
        System.out.println("Final result:");
        printPlayerInfo();
        Actor winner = game.getGameState() == GameState.PLAYER1_WON ? game.getPlayer1() : game.getPlayer2();
        System.out.println("Winner: " + winner.getName());
        scoreService.addScore(new Score("Slide-A-Lama",winner.getName(),winner.getScore(), new Date()));
        ratingService.setRating(new Rating("Slide-A-Lama",winner.getName(),getPlayerRating(), new Date()));
        comment.addComment(new Comment("Slide-A-Lama", winner.getName(), getPLayerComment(), new  Date()));
    }

    private String getPLayerComment() {
        var comment = "";
        while(comment.isEmpty() || comment.length() > 75){
            System.out.println("Enter your comment[max 75 symbols]: ");
            if(scanner.hasNext()) {
                comment = scanner.nextLine();
                //scanner.nextLine();
            }
        }
        return comment;
    }

    private void moveResult() {
        if (game.getLastMoveScore() > 0) {
            System.out.println("Combination found! +" + game.getLastMoveScore() + " points");

            if (game.getLastMoveLamasWon() > 0) {
                System.out.println(game.getLastMoveLamasWon() + " lama(s) taken from opponent");
            }
        } else {
            System.out.println("No combination found.");
        }
    }

    private boolean isRatingValid(int rating){
        return rating >= 0 && rating <= 5;
    }

    private int getPlayerRating(){
        int rating = -1;
        while(!isRatingValid(rating)){
            System.out.println("Enter your rating[0-5]: ");
            if(scanner.hasNext()) {
                rating = scanner.nextInt();
            }
        }
        return rating;
    }

    private void printPlayerInfo() {
        System.out.println(game.getPlayer1().getName() + " score: " + game.getPlayer1().getScore());
        System.out.println(game.getPlayer1().getName() + " lamas: " + game.getPlayer1().getLamaCount());

        System.out.println(game.getPlayer2().getName() + " score: " + game.getPlayer2().getScore());
        System.out.println(game.getPlayer2().getName() + " lamas: " + game.getPlayer2().getLamaCount());

    }
    private void printBoard () {
        TileType[][] grid = game.getBoard().getGrid();
        System.out.println("  1\t2\t3\t4\t5");
        int quickIndex = 1;
        for (TileType[] tileTypes : grid) {
            System.out.print(quickIndex + "|");
            quickIndex++;
            for (int col = 0; col < tileTypes.length; col++) {
                System.out.print(tileTypes[col].getSymbol() + "\t");
            }
            System.out.println();
        }
    }

    private Direction chooseDirection() {
        while (true) {
            System.out.print("Choose direction: (L/R/T or X to exit): ");
            String direction = scanner.nextLine().toUpperCase(Locale.ROOT);
            System.out.println(direction);
            switch (direction) {
                case "L":
                    return Direction.LEFT;
                case "R":
                    return Direction.RIGHT;
                case "T":
                    return Direction.TOP;
                case "X":
                    return null;
                    //break;
                default:
                    System.out.println("Invalid direction");
                    System.out.println("Try again");
            }
        }
    }

    private int chooseIndex(Direction direction) {
        if (direction == Direction.TOP) {
            System.out.print("Choose column (1-5): ");
        } else {
            System.out.print("Choose row (1-5): ");
        }
        int index = -1;
        while (index < 1 || index > 5) {
            if(scanner.hasNextInt()) {
                index = scanner.nextInt();
                scanner.nextLine();
            }else
                scanner.nextLine();
            System.out.println("Invalid index");
            System.out.println("Try again (1-5)");
        }
        return index - 1;
    }

    public BotDifficulty chooseBotDifficulty() {
        int difficulty = -100;
        while (difficulty != 1 && difficulty != 2){
            System.out.println("Choose bot difficulty: ");
            System.out.println("1 --- Easy");
            System.out.println("2 --- Hard");

            if (scanner.hasNextInt()) {
                 difficulty = scanner.nextInt();
            }else {
                System.out.println("Invalid choice");
                System.out.println("Try again");
            }
            scanner.nextLine();
        }

        return difficulty == 1 ? BotDifficulty.EASY : BotDifficulty.HARD;
    }
}
