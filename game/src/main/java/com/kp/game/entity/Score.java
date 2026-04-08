package com.kp.game.entity;

import jakarta.persistence.*;

import java.util.Date;

//Hibernate буде сприймати Score як обєкт який можна зберігати в таблицю , видаляти ,оновлювати ,читати з таблиці
@Entity
@Table(name = "score")
public class Score {

    @Id
    //Це означає, що id буде генеруватися автоматично базою даних.Не треба вручну писати чому дорівнює айді
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //JPA через id розуміє, який саме рядок у базі відповідає якому об’єкту.
    private int id;

    private String game;
    private String player;
    private int points;

    //зберігає і дату і час
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "played_on")
    private Date playedOn;

    //Hibernate часто створює об’єкти сам, коли читає дані з таблиці.
    //Для цього йому потрібен конструктор без параметрів.
    public Score() {
    }

    public Score(String game, String player, int points, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
    }

    public int getId() {
        return id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + playedOn +
                '}';
    }

}
