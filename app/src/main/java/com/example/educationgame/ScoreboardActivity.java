package com.example.educationgame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreboardActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SQLiteOpenHelper educationDatabaseHelper = new EducationDatabaseHelper(this);
        ListView listNames = (ListView) findViewById(R.id.score_name);

        try {
            db = educationDatabaseHelper.getReadableDatabase();
            cursor = db.query("HIGHSCORE",
                    new String[]{"_id", "USERNAME", "SCORE", "TIME"}, null, null, null, null, "SCORE DESC");

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.item_score, cursor, new String[]{"USERNAME", "SCORE", "TIME"}, new int[]{R.id.item_username, R.id.item_score, R.id.item_time}, 0);
            listNames.setAdapter(listAdapter);


        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
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