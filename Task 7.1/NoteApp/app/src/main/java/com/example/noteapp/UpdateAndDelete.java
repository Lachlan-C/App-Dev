package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapp.data.DatabaseHelper;
import com.example.noteapp.model.Note;

public class UpdateAndDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_and_delete);
        DatabaseHelper db;
        String noteData = null;
        Integer noteid = null;
        EditText EditTextBox;
        Button updatebutton, deletebutton;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noteData = extras.getString("note");
            noteid = extras.getInt("note_id");

        }
        EditTextBox = findViewById(R.id.EditNoteBox);
        updatebutton = findViewById(R.id.UpdateButton);
        deletebutton = findViewById(R.id.DeleteButton);

        EditTextBox.setText(noteData);
        db = new DatabaseHelper(this);

        Integer finalNoteid = noteid;
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updateRow = db.updateNote(new Note(finalNoteid, EditTextBox.getText().toString()));
                if (updateRow > 0)
                {
                    Toast.makeText(UpdateAndDelete.this, "Updated successfully!", Toast.LENGTH_SHORT).show();
                    Finish();
                }
                else
                {
                    Toast.makeText(UpdateAndDelete.this, "No row found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteNote(finalNoteid);
                Toast.makeText(UpdateAndDelete.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                Finish();
            }
        });
    }
    private void Finish()
    {
        Intent intent = new Intent();
        setResult(1, intent);;
        finish();
    }
}