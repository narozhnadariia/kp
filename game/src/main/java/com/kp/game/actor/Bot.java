package com.kp.game.actor;

import com.kp.game.core.Board;

public abstract class Bot extends Actor {

    public Bot(String name) {
        super(name);
    }
    public abstract Move chooseMove(Board board);
}