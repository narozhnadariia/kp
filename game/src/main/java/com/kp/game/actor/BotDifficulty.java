package com.kp.game.actor;

public enum BotDifficulty {
    EASY ,
    HARD;
    public String botNameDifficulty() {
        return this == EASY ? "Easy" : "Hard";
    }
}
