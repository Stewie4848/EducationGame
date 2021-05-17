package com.example.educationgame;

public class Game {
    public int difficulty;
    private int seconds;
    public int time_setting;
    public int score = 0;

    Game() {
        seconds = time_setting;
    }

    public void setDifficulty(int i) {
        difficulty = i;
    }

    public void setTime_setting(int time) {
        seconds = time;
    }

    void tick() {
        --seconds;
    }

    public String getSeconds() {
        return Integer.toString(seconds);
    }

    public String getScore() {
        return Integer.toString(score);
    }

    public void addScore() {
        score += difficulty * 100;

    }

    public void removeScore() {
        score -= difficulty * 2 * 100;
        if (score <= 0) {
            score = 0;
        }
    }

}

