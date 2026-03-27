package com.kp.game.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileTypeTest {

    @Test
    void getPoints_shouldReturnBasePointsForThreeSymbols() {
        assertEquals(20, TileType.BANANA.getPoints(3));
        assertEquals(10, TileType.BELL.getPoints(3));
        assertEquals(150, TileType.SEVEN.getPoints(3));
    }

    @Test
    void getPoints_shouldReturnDoublePointsForFourSymbols() {
        assertEquals(40, TileType.BANANA.getPoints(4));
        assertEquals(20, TileType.BELL.getPoints(4));
        assertEquals(300, TileType.SEVEN.getPoints(4));
    }

    @Test
    void getPoints_shouldReturnTriplePointsForFiveSymbols() {
        assertEquals(60, TileType.BANANA.getPoints(5));
        assertEquals(30, TileType.BELL.getPoints(5));
        assertEquals(450, TileType.SEVEN.getPoints(5));
    }

    @Test
    void getPoints_shouldReturnZeroForLessThanThreeSymbols() {
        assertEquals(0, TileType.BANANA.getPoints(1));
        assertEquals(0, TileType.BANANA.getPoints(2));
    }

    @Test
    void getPoints_shouldReturnZeroForMoreThanFiveSymbols() {
        assertEquals(0, TileType.BANANA.getPoints(6));
    }

    @Test
    void getSymbol_shouldReturnCorrectSymbol() {
        assertEquals("7", TileType.SEVEN.getSymbol());
        assertEquals("BAR", TileType.BAR.getSymbol());
        assertEquals("C", TileType.CHERRY.getSymbol());
        assertEquals("PE", TileType.PEAR.getSymbol());
        assertEquals("PL", TileType.PLUM.getSymbol());
        assertEquals("BA", TileType.BANANA.getSymbol());
        assertEquals("BE", TileType.BELL.getSymbol());
    }
}