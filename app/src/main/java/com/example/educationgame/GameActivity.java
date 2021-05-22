package com.example.educationgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.squareup.seismic.ShakeDetector;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements ShakeDetector.Listener {
    private final Game game = new Game();
    private boolean isRunning;
    private TextView timer;
    private TextView question;
    private TextView score;
    private ProgressBar progressBar;
    private Handler handler;
    private static final int[] buttons = {
            R.id.button1, R.id.button2, R.id.button3, R.id.button4
    };
    private String[] questions;
    private int questionArray;
    private int answerArray;
    private final Random random = new Random();
    private String[] answers;
    private final ArrayList<Integer> questionOrder = new ArrayList<>();
    private int correctButton;
    private int correctAnswers = 0;
    private int skippedQuestions = 0;
    private int wrongAnswers = 0;
    private int shaken = 0;
    private TextView shakeAmount;
    private Vibrator vibrator;
    private ShakeDetector sd;
    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Set up sensor & vibration

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Create variables from View
        timer = findViewById(R.id.timer);
        question = findViewById(R.id.question);
        score = findViewById(R.id.score);
        progressBar = findViewById(R.id.progressBar);
        shakeAmount = findViewById(R.id.shakeAmount);


        getSettings();
        enableTimer();
        setDifficulty();

        getQuestions();
        startQuestions();

    }

    @Override
    protected void onResume() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd = new ShakeDetector(this);
        sd.start(sensorManager);
        super.onResume();

    }

    @Override
    protected void onPause() {
        sd.stop();
        sensorManager.unregisterListener(sd);
        super.onPause();

    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(this::enableTimer);
        game.seconds = -1;
        super.onDestroy();
    }

    // Note: Timer does not stop during game in order to prevent cheating!
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
                    progressBar.setProgress(game.time - (-game.seconds + game.time));
                    if (game.seconds == 0) {
                        isRunning = false;
                        gameComplete();

                    }
                    if (game.seconds < 0) {
                        isRunning = false;
                        finish();
                    }
                }
            }
        });
    }


    public void buttonPressed(View view) {
        if (view.getId() == correctButton) {
            game.addScore();
            System.out.println("Correct");
            correctAnswers += 1;

        } else {
            game.removeScore();
            System.out.println("Wrong");
            wrongAnswers += 1;
        }
        updateScore();
        newQuestion();


    }


    private void startQuestions() {
        int i = random.nextInt(questions.length - 1);
        questionOrder.add(i);
        question.setText(questions[i]);
        setAnswers(i);
    }


    private void newQuestion() {
        boolean state = true;
        int j = random.nextInt(questions.length - 1);
        while (state) {
            if (questionOrder.contains(j)) {
                j = random.nextInt(questions.length - 1);
            }
            if (questionOrder.size() == questions.length) {
                gameComplete();
                finish();
                break;
            } else {
                questionOrder.add(j);
                question.setText(questions[j]);
                state = false;
            }
        }
        System.out.println(questionOrder.size());
        System.out.println(questions.length);
        System.out.println("Time " + game.seconds);
        setAnswers(j);


    }

    private void getQuestions() {
        questions = getResources().getStringArray(questionArray);
        answers = getResources().getStringArray(answerArray);


    }

    private void setDifficulty() {
        shakeAmount.setText(String.valueOf(game.skipTotal));
        switch (game.difficulty) {
            case 1:
                questionArray = R.array.easy_questions;
                answerArray = R.array.easy_answers;
                break;

            case 2:
                questionArray = R.array.medium_questions;
                answerArray = R.array.medium_answers;
                break;

            case 3:
                questionArray = R.array.hard_questions;
                answerArray = R.array.hard_answers;
                break;
        }
    }


    private void gameComplete() {
        Intent intent = new Intent(this, GameComplete.class);
        intent.putExtra("score", game.score);
        intent.putExtra("Correct", correctAnswers);
        intent.putExtra("Wrong", wrongAnswers);
        intent.putExtra("Username", game.username);
        intent.putExtra("Time", game.seconds);
        finish();
        startActivity(intent);



    }

    // Assign answers to each button
    private void setAnswers(int questionID) {
        ArrayList<Integer> questionButtons = new ArrayList<>();

        // Sets an answer for each button
        for (int value : buttons) {
            Button button = findViewById(value);
            int i = random.nextInt(answers.length - 1);
            while (questionButtons.contains(i) || i == questionID) {
                i = random.nextInt(answers.length - 1);
            }
            questionButtons.add(i);
            button.setText(answers[i]);

        }

        // Finds a random button and assigns the correct answer
        int b = random.nextInt(buttons.length - 1);
        Button button = findViewById(buttons[b]);
        button.setText(answers[questionID]);
        correctButton = button.getId();


    }

    private void updateScore() {
        score.setText(game.getScore());
        System.out.println(game.getScore());
    }

    private void getSettings() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        game.username = settings.getString("username", getString(R.string.default_username));
        game.difficulty = Integer.parseInt(settings.getString("difficulty_setting", "1"));
        game.setTime(Integer.parseInt(settings.getString("time_setting", "60")));
        game.setSkipTotal(Integer.parseInt(settings.getString("skip_setting", "3")));
        progressBar.setMax(game.time);

    }

    @Override
    public void hearShake() {
        shaken += 1;

        // Vibrate phone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(500);
        }

        System.out.println("Shaken: " + shaken);
        skipQuestion();
    }

    private void skipQuestion() {
        if (skippedQuestions < game.skipTotal) {
            skippedQuestions += 1;
            shakeAmount.setText(String.valueOf(game.skipTotal - skippedQuestions));
            newQuestion();
        } else {
            Toast.makeText(this, "No skips left!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        isRunning = false;
        finish();
        super.onBackPressed();
    }


}