package com.example.educationgame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameComplete extends AppCompatActivity {

    private EducationDatabaseHelper dbHelper = new EducationDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complete);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int score = bundle.getInt("score", 0);
            int correct = bundle.getInt("Correct", 0);
            int wrong = bundle.getInt("Wrong", 0);
            String username = bundle.getString("Username", "Generic Gantalope");
            int time = bundle.getInt("Time", 0);


            TextView scoreView = findViewById(R.id.score_game_complete);
            TextView correctView = findViewById(R.id.correct_amount);
            TextView wrongView = findViewById(R.id.wrong_amount);

            scoreView.setText(String.valueOf(score));
            correctView.setText(String.valueOf(correct));
            wrongView.setText(String.valueOf(wrong));


            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("USERNAME", username);
            values.put("SCORE", score);
            values.put("TIME", time);
            db.insert("HIGHSCORE", null, values);


        }


    }

    public void scoreboardPressed(View view) {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
        finish();

    }

    public void tryAgain(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void tweet(View view) {

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}