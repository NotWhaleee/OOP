package ru.nsu.kozorez;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {

    private String title;
    private String note;
    final private Date date;

    public Note(String title, String note, Date date) {
        this.title = title;
        this.note = note;
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        DateFormat dform = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        return "Title: " + title + "\nCreation date: " + dform.format(date) + "\n" + note + "\n";
    }

}
