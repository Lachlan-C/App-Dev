package com.example.youtubeplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button playbutton;
    EditText urlInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playbutton = findViewById(R.id.PlayButton);
        urlInput = findViewById(R.id.TextURLInput);

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlInput.getText().toString();
                Intent intent = new Intent(MainActivity.this, Video.class);
                String[] urlID = url.split("=");
                intent.putExtra("URL",urlID[1]);
                startActivity(intent);
            }
        });
    }
}