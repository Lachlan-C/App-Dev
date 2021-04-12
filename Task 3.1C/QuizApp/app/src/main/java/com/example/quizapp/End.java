package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class End extends AppCompatActivity {

    Button Close, Reset;
    TextView ShowScore, CongratsPlayer;
    String Score, QuestionsNum;
    String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        ShowScore = findViewById(R.id.EndScore);
        CongratsPlayer = findViewById(R.id.Congrats);
        Close = findViewById(R.id.Close);
        Reset = findViewById(R.id.Reset);

        Score = getIntent().getStringExtra("Score");
        QuestionsNum = getIntent().getStringExtra("TotalQuestions");
        UserName = getIntent().getStringExtra("UserName");

        CongratsPlayer.setText("Congradulations " + UserName);
        ShowScore.setText(Score + "/" + QuestionsNum);

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StartGame = new Intent(End.this , MainActivity.class);
                StartGame.putExtra("UserName",UserName);
                startActivity(StartGame);
            }
        });

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });

    }
}