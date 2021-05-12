package com.example.noteapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.noteapp.model.Note;
import com.example.noteapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "tag";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " (" + Util.NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Util.NOTE_DATA + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_NOTE_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_NOTE_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);

    }

    public long insertNote (Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NOTE_DATA, note.getNote());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public boolean fetchNote(String note)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.NOTE_ID}, Util.NOTE_DATA + "=?",
                new String[] {note}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();

        if (numberOfRows > 0)
            return  true;
        else
            return false;
    }

    public List<Note> fetchAllNotes (){
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNote_id(cursor.getInt(0));
                note.setNote(cursor.getString(1));

                noteList.add(note);

            } while (cursor.moveToNext());

        }

        return noteList;
    }

    public int updateNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NOTE_DATA, note.getNote());

        return db.update(Util.TABLE_NAME, contentValues, Util.NOTE_ID + "=?", new String[]{String.valueOf(note.getNote_id())});
    }
    public void deleteNote(Integer note_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.NOTE_ID + "=?", new String[]{String.valueOf(note_id)});
        db.close();
    }
}
