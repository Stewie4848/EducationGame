package com.example.educationgame;

public class Game {
    public int difficulty;
    public int seconds;
    public int time;
    public int score = 0;
    public String username;

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
        score += difficulty * 100;

    }

    public void removeScore() {
        score -= difficulty * 2 * 100;
        if (score <= 0) {
            score = 0;
        }
    }

}

