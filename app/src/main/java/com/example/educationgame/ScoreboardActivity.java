package com.example.educationgame;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ScoreboardActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_activity);

        SQLiteOpenHelper edcationDatabaseHelper = new EducationDatabaseHelper(this);
        ListView listScores = (ListView) findViewById(R.id.score_score);
        ListView listNames = (ListView) findViewById(R.id.score_name);
        ListView listTimes = (ListView) findViewById(R.id.score_time);

        try {
            db = edcationDatabaseHelper.getReadableDatabase();
            cursor = db.query("HIGHSCORE",
                    new String[]{"_id", "USERNAME", "SCORE", "TIME"}, null, null, null, null, "SCORE DESC");

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"USERNAME"}, new int[]{android.R.id.text1}, 0);
            listNames.setAdapter(listAdapter);

            listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"SCORE"}, new int[]{android.R.id.text1}, 0);
            listScores.setAdapter(listAdapter);

            listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"TIME"}, new int[]{android.R.id.text1}, 0);
            listTimes.setAdapter(listAdapter);



        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}