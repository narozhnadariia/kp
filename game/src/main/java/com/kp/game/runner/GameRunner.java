package com.kp.game.runner;

import com.kp.game.consoleui.ConsoleUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//він говорит до спрінг щоб він викликав цей клас де це потрібно
@Component
public class GameRunner implements CommandLineRunner {//гра почнеться тоді коли все перевірено і готово дякуючи цьому
    private final ConsoleUI consoleUI;
//@Autowired = Dependency Injection(Впровадження залежностей)
    public GameRunner(@Autowired ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }
    @Override
    public void run(String... args) throws Exception {
        consoleUI.startGame();
    }
}
