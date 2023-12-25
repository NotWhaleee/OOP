package ru.nsu.kozorez;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class for representing a note.
 */
public class Note {

    private final String title;
    private final String note;
    private final Date date;

    /**
     * constructor.
     *
     * @param title string.
     * @param note string.
     * @param date date.
     */
    public Note(String title, String note, Date date) {
        this.title = title;
        this.note = note;
        this.date = date;
    }

    /**
     * get the text of the note.
     *
     * @return string.
     */
    public String getNote() {
        return note;
    }

    /**
     * get the creation date.
     *
     * @return date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * get the title.
     *
     * @return string.
     */
    public String getTitle() {
        return title;
    }


    /**
     * string representation of the note.
     *
     * @return string.
     */
    @Override
    public String toString() {
        DateFormat dform = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        return "Title: " + title + "\nCreation date: " + dform.format(date) + "\n" + note + "\n";
    }

}
