package com.kp.game.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void shiftRowLeft_shouldInsertNewTileAtBeginning() {
        Board board = new Board(1, 5);

        board.setTile(0, 0, TileType.BANANA);
        board.setTile(0, 1, TileType.BELL);
        board.setTile(0, 2, TileType.PLUM);
        board.setTile(0, 3, TileType.PEAR);
        board.setTile(0, 4, TileType.CHERRY);

        board.shiftRowLeft(0, TileType.BAR);

        assertEquals(TileType.BAR, board.getTile(0, 0));
        assertEquals(TileType.BANANA, board.getTile(0, 1));
        assertEquals(TileType.BELL, board.getTile(0, 2));
        assertEquals(TileType.PLUM, board.getTile(0, 3));
        assertEquals(TileType.PEAR, board.getTile(0, 4));
    }

    @Test
    void shiftRowRight_shouldInsertNewTileAtEnd() {
        Board board = new Board(1, 5);

        board.setTile(0, 0, TileType.BANANA);
        board.setTile(0, 1, TileType.BELL);
        board.setTile(0, 2, TileType.PLUM);
        board.setTile(0, 3, TileType.PEAR);
        board.setTile(0, 4, TileType.CHERRY);

        board.shiftRowRight(0, TileType.BAR);

        assertEquals(TileType.BELL, board.getTile(0, 0));
        assertEquals(TileType.PLUM, board.getTile(0, 1));
        assertEquals(TileType.PEAR, board.getTile(0, 2));
        assertEquals(TileType.CHERRY, board.getTile(0, 3));
        assertEquals(TileType.BAR, board.getTile(0, 4));
    }

    @Test
    void shiftColTop_shouldInsertNewTileAtTop() {
        Board board = new Board(5, 1);

        board.setTile(0, 0, TileType.BANANA);
        board.setTile(1, 0, TileType.BELL);
        board.setTile(2, 0, TileType.PLUM);
        board.setTile(3, 0, TileType.PEAR);
        board.setTile(4, 0, TileType.CHERRY);

        board.shiftColTop(0, TileType.BAR);

        assertEquals(TileType.BAR, board.getTile(0, 0));
        assertEquals(TileType.BANANA, board.getTile(1, 0));
        assertEquals(TileType.BELL, board.getTile(2, 0));
        assertEquals(TileType.PLUM, board.getTile(3, 0));
        assertEquals(TileType.PEAR, board.getTile(4, 0));
    }

    @Test
    void culculateHorizontalScore_shouldReturnPointsForThreeSameTiles() {
        Board board = new Board(1, 5);

        board.setTile(0, 0, TileType.BANANA);
        board.setTile(0, 1, TileType.BANANA);
        board.setTile(0, 2, TileType.BANANA);
        board.setTile(0, 3, TileType.PEAR);
        board.setTile(0, 4, TileType.CHERRY);

        int score = board.culculateHorizontalScore();

        assertEquals(TileType.BANANA.getPoints(3), score);
    }

    @Test
    void calculateVerticalScore_shouldReturnPointsForThreeSameTiles() {
        Board board = new Board(3, 3);

        board.setTile(0, 0, TileType.BANANA);
        board.setTile(1, 0, TileType.BANANA);
        board.setTile(2, 0, TileType.BANANA);

        board.setTile(0, 1, TileType.BELL);
        board.setTile(1, 1, TileType.PLUM);
        board.setTile(2, 1, TileType.PEAR);

        board.setTile(0, 2, TileType.CHERRY);
        board.setTile(1, 2, TileType.BAR);
        board.setTile(2, 2, TileType.SEVEN);

        int score = board.calculateVerticalScore();

        assertEquals(TileType.BANANA.getPoints(3), score);
    }

    @Test
    void calculateTotalScore_shouldReturnSumOfHorizontalAndVerticalScores() {
        Board board = new Board(3, 3);

        board.setTile(0, 0, TileType.BANANA);
        board.setTile(0, 1, TileType.BANANA);
        board.setTile(0, 2, TileType.BANANA);

        board.setTile(1, 0, TileType.BELL);
        board.setTile(1, 1, TileType.PLUM);
        board.setTile(1, 2, TileType.PEAR);

        board.setTile(2, 0, TileType.CHERRY);
        board.setTile(2, 1, TileType.BAR);
        board.setTile(2, 2, TileType.SEVEN);

        int score = board.calculateTotalScore();

        assertEquals(TileType.BANANA.getPoints(3), score);
    }

    @Test
    void copy_shouldCreateIndependentBoardCopy() {
        Board board = new Board(1, 3);

        board.setTile(0, 0, TileType.BANANA);
        board.setTile(0, 1, TileType.BELL);
        board.setTile(0, 2, TileType.PLUM);

        Board copy = board.copy();
        copy.setTile(0, 0, TileType.BAR);

        assertEquals(TileType.BANANA, board.getTile(0, 0));
        assertEquals(TileType.BAR, copy.getTile(0, 0));
    }

    @Test
    void clearMatchesAndFill_shouldRemoveHorizontalMatchedTiles() {
        Board board = new Board(3, 3);

        board.setTile(2, 0, TileType.BANANA);
        board.setTile(2, 1, TileType.BANANA);
        board.setTile(2, 2, TileType.BANANA);

        board.setTile(1, 0, TileType.BELL);
        board.setTile(1, 1, TileType.PLUM);
        board.setTile(1, 2, TileType.PEAR);

        board.setTile(0, 0, TileType.CHERRY);
        board.setTile(0, 1, TileType.BAR);
        board.setTile(0, 2, TileType.SEVEN);

        board.clearMatchesAndFill();


        assertNotNull(board.getTile(0, 0));
        assertNotNull(board.getTile(0, 1));
        assertNotNull(board.getTile(0, 2));

        assertEquals(TileType.BELL, board.getTile(2, 0));
        assertEquals(TileType.PLUM, board.getTile(2, 1));
        assertEquals(TileType.PEAR, board.getTile(2, 2));
    }

    @Test
    void clearMatchesAndFill_shouldRemoveVerticalMatchedTiles() {
        Board board = new Board(5, 5);

        board.setTile(2, 0, TileType.BANANA);
        board.setTile(3, 0, TileType.BANANA);
        board.setTile(4, 0, TileType.BANANA);
        board.setTile(0, 0, TileType.BELL);
        board.setTile(1, 0, TileType.SEVEN);


        board.setTile(0, 1, TileType.BELL);
        board.setTile(1, 1, TileType.PLUM);
        board.setTile(2, 1, TileType.PEAR);
        board.setTile(3, 1, TileType.BAR);
        board.setTile(4, 1, TileType.PLUM);

        board.setTile(0, 2, TileType.CHERRY);
        board.setTile(1, 2, TileType.BAR);
        board.setTile(2, 2, TileType.SEVEN);
        board.setTile(3, 2, TileType.PLUM);
        board.setTile(4, 2, TileType.SEVEN);

        board.printBoard();
        System.out.println();


        board.clearMatchesAndFill();

        board.printBoard();
        assertNotNull(board.getTile(0, 0));
        assertNotNull(board.getTile(1, 0));
        assertNotNull(board.getTile(2, 0));

        assertEquals(TileType.BELL, board.getTile(3, 0));
        assertEquals(TileType.SEVEN, board.getTile(4, 0));
    }
}