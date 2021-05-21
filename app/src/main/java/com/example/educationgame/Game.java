package com.example.educationgame;

public class Game {
    public int difficulty;
    public int seconds;
    public int time;
    public int score = 0;
    public String username;
    public int skipTotal;
    private int multiplier;

    Game() {
        seconds = time;
    }


    public void setTime(int t) {
        seconds = t;
        time = t;
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
        score += difficulty * 100 * multiplier;

    }

    public void removeScore() {
        score -= difficulty * 2 * 100;
        if (score <= 0) {
            score = 0;
        }
    }

    public void setSkipTotal(int skipAmount) {
        skipTotal = skipAmount;
        switch (skipTotal) {
            case 0:
                multiplier = 6;
            case 1:
                multiplier = 5;
            case 2:
                multiplier = 4;
            case 3:
                multiplier = 3;
            case 4:
                multiplier = 2;
            case 5:
                multiplier = 1;
        }
    }

}

