package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.Color;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

import static android.graphics.BlendMode.COLOR;

public class Gameplay extends AppCompatActivity {

    TextView WelcomeUser, QuestionTitle, QuestionText, QuestionNumber;
    ProgressBar pb;
    Button ChoiceOne, ChoiceTwo, ChoiceThree, Submit;
    int Question = 1, Score = 0, TotalQuestions = 5, UserSelectionChoice, Results = 0;
    String UserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        WelcomeUser = findViewById(R.id.WelcomeUser);
        pb = findViewById(R.id.ProgressBar);
        QuestionNumber = findViewById(R.id.QuestionNumber);
        QuestionTitle = findViewById(R.id.QuestionTitle);
        QuestionText = findViewById(R.id.QuestionText);
        ChoiceOne = findViewById(R.id.Choice1);
        ChoiceTwo = findViewById(R.id.Choice2);
        ChoiceThree = findViewById(R.id.Choice3);
        Submit = findViewById(R.id.Submit);

        UserName = getIntent().getStringExtra("UserName");

        WelcomeUser.setText("Welcome " + UserName);
        SetButtonsGray();
        UpdateProgress();
        getQuestion();

        ChoiceOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButtonsGray();
                ChoiceOne.setBackgroundColor(Color.GRAY);
                UserSelectionChoice = 1;
            }
        });

        ChoiceTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButtonsGray();
                ChoiceTwo.setBackgroundColor(Color.GRAY);
                UserSelectionChoice = 2;
            }
        });

        ChoiceThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetButtonsGray();
                ChoiceThree.setBackgroundColor(Color.GRAY);
                UserSelectionChoice = 3;
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Results == 0 && UserSelectionChoice != 0)
                {
                    Results = 1;
                    Submit.setText("Next");
                    ChoiceOne.setClickable(false);
                    ChoiceTwo.setClickable(false);
                    ChoiceThree.setClickable(false);
                    //Set Colour based on correct or wrong answer/Update score
                    getResults();
                } else if (Question == TotalQuestions) {
                    //Go to End Activity
                    //Intent pass score, total number, name
                    Intent EndGame = new Intent(Gameplay.this , End.class);
                    EndGame.putExtra("UserName", UserName);
                    EndGame.putExtra("TotalQuestions",String.valueOf(TotalQuestions));
                    EndGame.putExtra("Score",String.valueOf(Score));
                    startActivity(EndGame);
                } else if (Results == 1) {
                    Results = 0;
                    Submit.setText("Submit");
                    Question += 1;
                    UpdateProgress();
                    //Get New Question/Title/Answers
                    getQuestion();
                    SetButtonsGray();
                    UserSelectionChoice = 0;
                    ChoiceOne.setClickable(true);
                    ChoiceTwo.setClickable(true);
                    ChoiceThree.setClickable(true);
                }
            }
        });
    }

    public void UpdateProgress()
    {
        if (Question > 1) WelcomeUser.setText("");
        float percent = ((float) Question/(float) TotalQuestions)*100;
        pb.setProgress((int) percent);
        QuestionNumber.setText(Question + "/" + TotalQuestions);
    }

    public void SetButtonsGray()
    {
        ChoiceOne.setBackgroundColor(Color.LTGRAY);
        ChoiceTwo.setBackgroundColor(Color.LTGRAY);
        ChoiceThree.setBackgroundColor(Color.LTGRAY);
    }

    public void getResults() {
        switch (Question) {
            case 1:
                //Answer 1
                ChoiceOne.setBackgroundColor(Color.GREEN);
                switch (UserSelectionChoice) {
                    case 1:
                        Score++;
                        break;
                    case 2:
                        ChoiceTwo.setBackgroundColor(Color.RED);
                        break;
                    case 3:
                        ChoiceThree.setBackgroundColor(Color.RED);
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                //Answer 2
                ChoiceTwo.setBackgroundColor(Color.GREEN);
                switch (UserSelectionChoice) {
                    case 1:
                        ChoiceOne.setBackgroundColor(Color.RED);
                        break;
                    case 2:
                        Score++;
                        break;
                    case 3:
                        ChoiceThree.setBackgroundColor(Color.RED);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                //Answer 3
                ChoiceThree.setBackgroundColor(Color.GREEN);
                switch (UserSelectionChoice) {
                    case 1:
                        ChoiceOne.setBackgroundColor(Color.RED);
                        break;
                    case 2:
                        ChoiceTwo.setBackgroundColor(Color.RED);
                        break;
                    case 3:
                        Score++;
                        break;
                    default:
                        break;
                }
                break;
            case 4:
                //Answer 3
                ChoiceThree.setBackgroundColor(Color.GREEN);
                switch (UserSelectionChoice) {
                    case 1:
                        ChoiceOne.setBackgroundColor(Color.RED);
                        break;
                    case 2:
                        ChoiceTwo.setBackgroundColor(Color.RED);
                        break;
                    case 3:
                        Score++;
                        break;
                    default:
                        break;
                }
                break;
            case 5:
                //answer 1
                ChoiceOne.setBackgroundColor(Color.GREEN);
                switch (UserSelectionChoice) {
                    case 1:
                        Score++;
                        break;
                    case 2:
                        ChoiceTwo.setBackgroundColor(Color.RED);
                        break;
                    case 3:
                        ChoiceThree.setBackgroundColor(Color.RED);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    public  void getQuestion() {
        switch (Question) {
            case 1:
                QuestionTitle.setText("What is Android?");
                QuestionText.setText("What part of a phone is Android?");
                ChoiceOne.setText("Operating System");
                ChoiceTwo.setText("Hardware");
                ChoiceThree.setText("Application");
                break;
            case 2:
                QuestionTitle.setText("Kernel");
                QuestionText.setText("What kernel does does android use within the OS?");
                ChoiceOne.setText("FreeBSD kernel");
                ChoiceTwo.setText("Linux kernel");
                ChoiceThree.setText("Windows kernel");
                break;
            case 3:
                QuestionTitle.setText("Release Date");
                QuestionText.setText("When was Android v1.0 Released?");
                ChoiceOne.setText("January 2008");
                ChoiceTwo.setText("April 2007");
                ChoiceThree.setText("September 2008");
                break;
            case 4:
                QuestionTitle.setText("Language Choice");
                QuestionText.setText("In what programming language are Android apps written in?");
                ChoiceOne.setText("Python");
                ChoiceTwo.setText("SQL");
                ChoiceThree.setText("Java");
                break;
            case 5:
                QuestionTitle.setText("Current Version");
                QuestionText.setText("What is the name of the version of Android released in 2018?");
                ChoiceOne.setText("Pie");
                ChoiceTwo.setText("Android 10");
                ChoiceThree.setText("KitKat");
                break;
            default:
                break;
        }
    }
}

