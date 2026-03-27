package com.kp.game.actor;

import com.kp.game.core.Board;
import com.kp.game.core.Direction;

public class EasyBot extends Bot {
    public EasyBot() {
        super("Easy Bot");
    }

    @Override
    public Move chooseMove(Board board) {
        Direction[] direction = Direction.values();
        Direction randomDirection = direction[(int)(Math.random() * direction.length)];
        int index;

        if(randomDirection == Direction.TOP)
            index = (int)(Math.random() * board.getColsSum());
        else
            index = (int)(Math.random() * board.getRowsSum());

        return new Move(randomDirection, index);
    }
}
