package com.example.educationgame;

public class Game {
    public String difficulty;
    private int seconds;
    public int time_setting;

    Game() {seconds = time_setting; }

    public void setDifficulty(String d) {
        difficulty = d;
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
    
}
