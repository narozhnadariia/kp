package com.kp.game.service.dto;

public record FeedbackRequest(
        Long gameId,
        String winner,
        String player1Name,
        String player2Name,
        int player1Score,
        int player2Score,
        int rating,
        String comment
) {
}