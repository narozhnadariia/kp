package com.kp.game.actor;

public abstract class Actor {
    protected String name;
    protected int score;
    protected int lamaCount;
    protected int lamaMilestones;

    public Actor(String name) {
        this.name = name;
        this.score = 0;
        this.lamaCount = 5;
        this.lamaMilestones = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getLamaCount() {
        return lamaCount;
    }

    public void increaseLamaCount() {
        this.lamaCount++;
    }

    public void decreaseLamaCount() {
        if (lamaCount > 0) {
            this.lamaCount--;
        }
    }

    public void increaseScore(int points) {
        this.score += points;
    }

    public int getLamaMilestones() {
        return lamaMilestones;
    }

    public void setLamaMilestones(int lamaMilestones) {
        this.lamaMilestones = lamaMilestones;
    }

}