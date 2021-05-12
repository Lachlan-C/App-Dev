package com.example.noteapp.model;

public class Note {
    private int note_id;
    private String note;

    public Note() {
    }

    public Note(int note_id, String note) {
        this.note_id = note_id;
        this.note = note;
    }

    public Note(String note) {
        this.note = note;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
