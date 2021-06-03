package com.example.foodrescuemobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodrescuemobileapp.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText usernameedittext = findViewById(R.id.PasswordInput);
        EditText passwordedittext = findViewById(R.id.UsernameInput);
        Button loginbutton = findViewById(R.id.LoginButton);
        Button signupbutton = findViewById(R.id.SignUpButton);


        db = new DatabaseHelper(this);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = db.fetchUser(usernameedittext.getText().toString(), passwordedittext.getText().toString());

                if (result) {
                    Toast.makeText(MainActivity.this, "LOGGED IN", Toast.LENGTH_SHORT).show();

                    int userID = db.fetchUserID(usernameedittext.getText().toString(), passwordedittext.getText().toString());
                    Intent intent = new Intent(MainActivity.this, home_screen.class);
                    intent.putExtra("user_id", userID);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupintent = new Intent(MainActivity.this, SignUpScreen.class);
                startActivity(signupintent);
            }
        });
    }
}