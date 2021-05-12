package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapp.data.DatabaseHelper;
import com.example.noteapp.model.Note;

public class NewNote extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Button savebutton = findViewById(R.id.SaveButton);
        EditText text = findViewById(R.id.EditNoteTextBox);
        db = new DatabaseHelper(this);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = text.getText().toString();
                long result = db.insertNote(new Note(data));
                if (result > 0)
                    {
                        Toast.makeText(NewNote.this, "Successfully Saved!", Toast.LENGTH_SHORT).show();
                        text.setText("");
                    }
                    else
                    {
                        Toast.makeText(NewNote.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }
}