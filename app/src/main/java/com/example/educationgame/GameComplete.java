package com.example.educationgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complete);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int score = bundle.getInt("score", 0);
            int correct = bundle.getInt("Correct", 0);
            int wrong = bundle.getInt("Wrong", 0);


            TextView scoreView = findViewById(R.id.score_game_complete);
            TextView correctView = findViewById(R.id.correct_amount);
            TextView wrongView = findViewById(R.id.wrong_amount);

            scoreView.setText(String.valueOf(score));
            correctView.setText(String.valueOf(correct));
            wrongView.setText(String.valueOf(wrong));
        }
    }

    public void scoreboardPressed(View view) {

    }

    public void tryAgain(View view) {

    }

    public void tweet(View view) {

    }


}