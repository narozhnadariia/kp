package com.kp.game.service.dto;
//Це дані одного гравця для frontend.
public record PlayerResponse(
        String name,
        int score,
        int lamas
) {
}