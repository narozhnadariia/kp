package com.kp.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//воно вказує що цей клас є головним , він налаштовує зєднання з базою данних
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
	}
}