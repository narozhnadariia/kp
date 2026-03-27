package com.kp.game.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void switchTurn_shouldChangeCurrentPlayer() {
        Game game = new Game(GameMode.PLAYER_VS_PLAYER, "Dariia", "Yaroslav", null);

        assertEquals("Dariia", game.getCurrentPlayer().getName());

        game.switchTurn();

        assertEquals("Yaroslav", game.getCurrentPlayer().getName());
    }

    @Test
    void getOpponent_shouldReturnSecondPlayerWhenCurrentPlayerIsFirst() {
        Game game = new Game(GameMode.PLAYER_VS_PLAYER, "Dariia", "Yaroslav", null);

        assertEquals("Yaroslav", game.getOpponent().getName());
    }

    @Test
    void getOpponent_shouldReturnFirstPlayerWhenCurrentPlayerIsSecond() {
        Game game = new Game(GameMode.PLAYER_VS_PLAYER, "Dariia", "Yaroslav", null);

        game.switchTurn();

        assertEquals("Dariia", game.getOpponent().getName());
    }

    @Test
    void checkWinner_shouldSetPlayer2WonWhenPlayer1HasNoLamas() {
        Game game = new Game(GameMode.PLAYER_VS_PLAYER, "Dariia", "Yaroslav", null);

        while (game.getPlayer1().getLamaCount() > 0) {
            game.getPlayer1().decreaseLamaCount();
        }

        game.checkWinner();

        assertEquals(GameState.PLAYER2_WON, game.getGameState());
    }

    @Test
    void checkWinner_shouldSetPlayer1WonWhenPlayer2HasNoLamas() {
        Game game = new Game(GameMode.PLAYER_VS_PLAYER, "Dariia", "Yaroslav", null);

        while (game.getPlayer2().getLamaCount() > 0) {
            game.getPlayer2().decreaseLamaCount();
        }

        game.checkWinner();

        assertEquals(GameState.PLAYER1_WON, game.getGameState());
    }

    @Test
    void isGameOver_shouldReturnFalseWhenGameIsPlaying() {
        Game game = new Game(GameMode.PLAYER_VS_PLAYER, "Dariia", "Yaroslav", null);

        assertFalse(game.isGameOver());
    }

    @Test
    void isGameOver_shouldReturnTrueWhenGameHasWinner() {
        Game game = new Game(GameMode.PLAYER_VS_PLAYER, "Dariia", "Yaroslav", null);

        while (game.getPlayer2().getLamaCount() > 0) {
            game.getPlayer2().decreaseLamaCount();
        }

        game.checkWinner();

        assertTrue(game.isGameOver());
    }

    @Test
    void processMove_shouldSwitchCurrentPlayerAfterMove() {
        Game game = new Game(GameMode.PLAYER_VS_PLAYER, "Dariia", "Yaroslav", null);

        String currentPlayerBeforeMove = game.getCurrentPlayer().getName();

        game.processMove(Direction.LEFT, 0);

        String currentPlayerAfterMove = game.getCurrentPlayer().getName();

        assertNotEquals(currentPlayerBeforeMove, currentPlayerAfterMove);
    }

    @Test
    void processMove_shouldGenerateNextTileAfterMove() {
        Game game = new Game(GameMode.PLAYER_VS_PLAYER, "Dariia", "Yaroslav", null);

        game.processMove(Direction.LEFT, 0);

        assertNotNull(game.getNextTile());
    }
}