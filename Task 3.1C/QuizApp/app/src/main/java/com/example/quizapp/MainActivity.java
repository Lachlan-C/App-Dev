package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.file.WatchEvent;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    Button start;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.startQuizButtton);
        nameInput = findViewById(R.id.UserNameInput);
        String UserName = getIntent().getStringExtra("UserName");
        nameInput.setText(UserName);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                if (name.isEmpty())
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Intent StartGame = new Intent(MainActivity.this , Gameplay.class);
                    StartGame.putExtra("UserName",name);
                    startActivity(StartGame);
                }
            }
        });
    }

}