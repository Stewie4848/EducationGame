package com.example.educationgame;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private Game game = new Game();
    private boolean isRunning;
    private TextView timer;
    private TextView question;
    private TextView score;
    private Handler handler;
    private static final int[] buttons = {
            R.id.button1, R.id.button2, R.id.button3, R.id.button4
    };
    private String[] questions;
    private int questionArray;
    private int answerArray;
    private final Random random = new Random();
    private String[] answers;
    private ArrayList<Integer> questionOrder = new ArrayList<>();
    private int correctButton;
//    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        timer = findViewById(R.id.timer);
        question = findViewById(R.id.question);
        game.setTime_setting(120);
        score = findViewById(R.id.score);


        game.setDifficulty(1);

        enableTimer();
        setDifficulty();
        getQuestions();
        startQuestions();

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


    public void buttonPressed(View view) {

        if (view.getId() == correctButton) {
            game.addScore();
            System.out.println("Correct");

        }
        else {
            game.removeScore();
            System.out.println("Wrong");
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
                break;
            } else {
                questionOrder.add(j);
                question.setText(questions[j]);
                state = false;
            }
        }
        System.out.println(questionOrder.size());
        System.out.println(questions.length);
        setAnswers(j);


    }

    private void getQuestions() {
        questions = getResources().getStringArray(questionArray);
        answers = getResources().getStringArray(answerArray);


    }

    private void setDifficulty() {
        switch (game.difficulty) {
            case 1:
                questionArray = R.array.easy_questions;
                answerArray = R.array.easy_answers;
                break;

//            case 2:
//                questionArray = R.array.medium_questions;
//                break;
//
//            case 3:
//                questionArray = R.array.hard_questions;
        }
    }


    private void gameComplete() {


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


}