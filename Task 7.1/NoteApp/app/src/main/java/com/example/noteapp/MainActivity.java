package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newnote = findViewById(R.id.NewNoteButton);
        Button allNotes = findViewById(R.id.ShowNotesButton);

        newnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewNoteIntent = new Intent(MainActivity.this, NewNote.class);
                startActivity(NewNoteIntent);
            }
        });
        allNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AllNotesIntent = new Intent(MainActivity.this, ShowAllNotes.class);
                startActivity(AllNotesIntent);
            }
        });
    }
}