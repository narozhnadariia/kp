package com.kp.game.service.dto;

import com.kp.game.actor.BotDifficulty;
import com.kp.game.core.GameMode;
//Це дані, які frontend відправить, коли створює гру.
public record CreateGameRequest(
        GameMode gameMode,
        String player1Name,
        String player2Name,
        BotDifficulty botDifficulty
) {
}