package com.kp.game.service.dto;

import com.kp.game.core.Direction;
//Це хід гравця: напрямок і номер рядка/колонки.
public record MoveRequest(
        Direction direction,
        int index
) {
}