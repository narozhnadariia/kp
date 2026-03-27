package com.kp.game.actor;

import com.kp.game.core.Board;
import com.kp.game.core.Direction;

public class HardBot extends Bot {
    public HardBot() {
        super("HardBot");
    }

    @Override
    public Move chooseMove(Board board) {
        Move bestmove = null;
        int bestScore = -1;//бо будь який хід дасть 0 або більше
        int limit;
        for (Direction direction : Direction.values()) {//проходжуся по всім напрямкам
            if(direction == Direction.TOP){
                limit = board.getColsSum();
            }else
                limit = board.getRowsSum();
            for (int i = 0; i < limit; i++) {
                int score = board.simulateMoveScore(direction,i);//бот тільки представляє скільки очок він отримає а не робить справжній хід

                if(score > bestScore){
                    bestScore = score;
                    bestmove = new Move(direction,i);
                }
            }
        }
        return bestmove;

    }
}
