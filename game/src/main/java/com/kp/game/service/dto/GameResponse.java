package com.kp.game.service.dto;

import com.kp.game.core.GameState;
import com.kp.game.core.TileType;
//Це повний стан гри, який backend повертає React-у.
public record GameResponse(
        Long id,
        TileType[][] board,
        TileType nextTile,
        PlayerResponse player1,
        PlayerResponse player2,
        String currentPlayerName,
        GameState gameState,
        int lastMoveScore,
        int lastMoveLamasWon,
        String winner
) {
}