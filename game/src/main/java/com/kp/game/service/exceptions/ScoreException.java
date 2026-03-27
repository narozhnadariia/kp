package com.kp.game.service.exceptions;
//цей клас викликається тоді коли є якісь помилки (наприклад коли бащза данних
// постгрес вимкненна тоді я точно буду знати що помилка не в логікі а в зберіганні очок)
public class ScoreException extends RuntimeException {//дякуючи цій функції я можу не засмічувати кожну
    // функцію в грі трай коч
    public ScoreException(String message) {
        super(message);
    }
//Throwable cause-справжня причина помилки
    public ScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
