package com.kp.game.actor;

import com.kp.game.core.Direction;

public class Move {
    private Direction direction;
    private int index;

    public Move(Direction direction, int index) {
        this.direction = direction;
        this.index = index;
    }
    public Direction getDirection() {
        return direction;
    }
    public int getIndex() {
        return index;
    }
}
