package com.example.educationgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private Game game = new Game();
    private boolean isRunning;
    private TextView timer;
    private TextView question;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        Game game = new Game();

        timer = findViewById(R.id.timer);
        question = findViewById(R.id.question);
        game.setTime_setting(60);

        enableTimer();



    }

    private void enableTimer() {
        isRunning = true;
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    game.tick();
                    timer.setText(game.getSeconds());
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }
}