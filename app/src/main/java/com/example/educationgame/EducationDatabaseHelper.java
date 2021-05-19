package com.example.educationgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EducationDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "education";
    private static final int DB_VERSION = 2;

    EducationDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion);
    }

    private static void insertScore(SQLiteDatabase db, String username, int score, int time) {
        ContentValues scoreValues = new ContentValues();
        scoreValues.put("USERNAME", username);
        scoreValues.put("SCORE", score);
        scoreValues.put("TIME", time);
        db.insert("HIGHSCORE", null, scoreValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE HIGHSCORE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "USERNAME TEXT, "
                    + "SCORE INTEGER, "
                    + "TIME INTEGER);");

            insertScore(db, "L33T HACKER", 10000, 36);
            insertScore(db, "Emon Lusk", 999, 20);


        }

    }
}
