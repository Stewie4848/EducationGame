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
        if (skipTotal == 0) {
            multiplier = 6;
        }
        if (skipTotal == 1) {
            multiplier = 5;
        }
        if (skipTotal == 2) {
            multiplier = 4;
        }
        if (skipTotal == 3) {
            multiplier = 3;
        }
        if (skipTotal == 4) {
            multiplier = 2;
        }
        if (skipTotal == 5) {
            multiplier = 1;
        }
    }

}

