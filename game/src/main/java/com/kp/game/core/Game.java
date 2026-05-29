package com.kp.game.core;

import com.kp.game.actor.*;

public class Game {
    private Board board;
    private Actor player1;
    private Actor player2;
    private Actor currentPlayer;
    private  GameState gameState;
    private GameMode gameMode;
    private TileType nextTile;
    private int lastMoveScore;
    private int lastMoveLamasWon;

    public Game(GameMode gameMode, String firstPlayerName, String secondPlayerName, BotDifficulty botDifficulty) {
        this.board = new Board(5,5);

        //збільшити мапу
//        this.board = new Board(6, 6);
        //

        this.board.fillRandom();
        this.gameMode = gameMode;
        this.player1 = new Player(firstPlayerName);
        this.nextTile = board.getRandomTile();
        this.lastMoveScore = 0;


        if (gameMode == GameMode.PLAYER_VS_BOT) {
            if(botDifficulty == BotDifficulty.EASY)
                this.player2 = new EasyBot();
            else
                this.player2 = new HardBot();
        }else {
            this.player2 = new Player(secondPlayerName);
        }
        this.currentPlayer = player1;
        this.gameState = GameState.PLAYING;
    }

    //крок назад
//    public Game copy() {
//        Game copyGame = new Game(
//                this.gameMode,
//                this.player1.getName(),
//                this.player2.getName(),
//                BotDifficulty.EASY
//        );
//
//        copyGame.board = this.board.copy();
//
//        copyGame.player1 = copyActor(this.player1);
//        copyGame.player2 = copyActor(this.player2);
//
//        copyGame.currentPlayer = this.currentPlayer == this.player1
//                ? copyGame.player1
//                : copyGame.player2;
//
//        copyGame.gameState = this.gameState;
//        copyGame.gameMode = this.gameMode;
//        copyGame.nextTile = this.nextTile;
//        copyGame.lastMoveScore = this.lastMoveScore;
//        copyGame.lastMoveLamasWon = this.lastMoveLamasWon;
//
//        return copyGame;
//    }
//


//    private Actor copyActor(Actor actor) {
//        Actor copy;
//
//        if (actor instanceof HardBot) {
//            copy = new HardBot();
//        } else if (actor instanceof EasyBot) {
//            copy = new EasyBot();
//        } else {
//            copy = new Player(actor.getName());
//        }
//
//        copy.increaseScore(actor.getScore());
//        copy.setLamaMilestones(actor.getLamaMilestones());
//
//        while (copy.getLamaCount() < actor.getLamaCount()) {
//            copy.increaseLamaCount();
//        }
//
//        while (copy.getLamaCount() > actor.getLamaCount()) {
//            copy.decreaseLamaCount();
//        }
//
//        return copy;
//    }
    //

    public boolean isGameOver() {
        return gameState != GameState.PLAYING;
    }

    // bottom row bananas
    public void makeBottomRowBananas() {
        board.fillBottomRowWithBananas();

        lastMoveScore = 0;
        lastMoveLamasWon = 0;

        checkWinner();
    }
//

    public void switchTurn() {
        if(currentPlayer == player1) {
            currentPlayer = player2;
        }else  {
            currentPlayer = player1;
        }
    }

    public void processMove(Direction direction, int index) {
        lastMoveLamasWon = 0;
        TileType newTile = nextTile;//генерується фішка яку я поставлю на поле

        if (direction == Direction.LEFT) {
            board.shiftRowLeft(index, newTile);
        } else if (direction == Direction.RIGHT) {
            board.shiftRowRight(index, newTile);
        } else if (direction == Direction.TOP) {
            board.shiftColTop(index, newTile);
        }

        int oldMilestones = currentPlayer.getLamaMilestones();

        lastMoveScore = board.calculateTotalScore();
        currentPlayer.increaseScore(lastMoveScore);

        if (lastMoveScore > 0) {
            board.clearMatchesAndFill();
        }

        int newMilestones = currentPlayer.getScore() / 70;
        int wonLamas = newMilestones - oldMilestones;

        if (wonLamas > 0) {
            lastMoveLamasWon = wonLamas;
            currentPlayer.setLamaMilestones(newMilestones);

            Actor opponent = getOpponent();

            for (int i = 0; i < wonLamas; i++) {
                if (opponent.getLamaCount() > 0) {
                    opponent.decreaseLamaCount();
                    currentPlayer.increaseLamaCount();
                }
            }
        }

        checkWinner();

        if (!isGameOver()) {
            switchTurn();
        }

        nextTile = board.getRandomTile();
    }

    // поміняти плити місцями
//    public void swapTiles(int row1, int col1, int row2, int col2) {
//        lastMoveScore = 0;
//        lastMoveLamasWon = 0;
//
//        board.swapTiles(row1, col1, row2, col2);
//
//        int score = board.calculateTotalScore();
//        lastMoveScore = score;
//        currentPlayer.increaseScore(score);
//
//        if (score > 0) {
//            board.clearMatchesAndFill();
//        }
//
//        checkWinner();
//
//        if (!isGameOver()) {
//            switchTurn();
//        }
//
//        nextTile = board.getRandomTile();
//    }
//

    //бомба
//    public void useBomb() {
//        lastMoveScore = 0;
//        lastMoveLamasWon = 0;
//
//        board.explodeRandomArea();
//
//        checkWinner();
//
//        if (!isGameOver()) {
//            switchTurn();
//        }
//
//        nextTile = board.getRandomTile();
//    }
    //

    public Actor getOpponent() {
        if (currentPlayer == player1) {
            return player2;
        } else {
            return player1;
        }
    }
    public void checkWinner() {
        if (player1.getLamaCount() == 0) {
            gameState = GameState.PLAYER2_WON;
        } else if (player2.getLamaCount() == 0) {
            gameState = GameState.PLAYER1_WON;
        }
    }


    public GameMode getGameMode() {
        return gameMode;
    }
    public GameState getGameState() {
        return gameState;
    }
    public Actor getCurrentPlayer() {
        return currentPlayer;
    }
    public Actor getPlayer1() {
        return player1;
    }
    public Actor getPlayer2() {
        return player2;
    }
    public Board getBoard() {
        return board;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public TileType getNextTile() {
        return nextTile;
    }

    public int getLastMoveScore() {
        return lastMoveScore;
    }
    public int getLastMoveLamasWon() {
        return lastMoveLamasWon;
    }
}
