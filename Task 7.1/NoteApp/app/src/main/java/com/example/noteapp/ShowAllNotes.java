package com.example.noteapp;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.noteapp.data.DatabaseHelper;
import com.example.noteapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class ShowAllNotes extends AppCompatActivity {
    DatabaseHelper db;
    ListView listView;
    ArrayList<String> noteArrayList;
    ArrayList<Integer> noteidArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_notes);
        Render();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            Render();
        }
    }
    private void Render()
    {
        listView = findViewById(R.id.listview);
        db = new DatabaseHelper(ShowAllNotes.this);
        noteArrayList = new ArrayList<>();
        noteidArrayList = new ArrayList<>();

        List<Note> noteList =db.fetchAllNotes();
        for(Note note :noteList)
        {
            noteArrayList.add(note.getNote());
            noteidArrayList.add(note.getNote_id());

        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowAllNotes.this, UpdateAndDelete.class);
                intent.putExtra("note", noteArrayList.get(position));
                intent.putExtra("note_id", noteidArrayList.get(position));
                startActivityForResult(intent, 1);
            }
        });
    }
}