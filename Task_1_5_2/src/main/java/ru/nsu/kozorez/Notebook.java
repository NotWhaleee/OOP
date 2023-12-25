package ru.nsu.kozorez;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;


/**
 * notebook class.
 */
public class Notebook {

    private Deque<Note> notes;

    private final String jsonFile = "notebook.json";

    /**
     * constructor.
     * uploads notes from the json file.
     */
    public Notebook() {
        readFromJson();
    }

    /**
     * prints all the notes.
     */
    public void show() {
        for (Note note : notes) {
            System.out.println(note);
        }
    }

    /**
     * converts all the notes to string.
     *
     * @return notes.
     */
    @Override
    public String toString() {
        String result = "";
        for (Note note : notes) {
            result = result.concat(note.toString());
        }
        return result;
    }

    /**
     * prints all the notes in the stated range with keywords in the title.
     * @param start prints notes from this date.
     * @param end prints notes up to that date.
     * @param keyWords keywords in the title regardless of the register.
     */
    public void show(Date start, Date end, String[] keyWords) {
        Stream<Note> noteStream = notes.stream()
                .filter(note -> isWithinDateRange(note, start, end))
                .filter(note -> containsKeyword(note, keyWords));

        noteStream.forEach(System.out::println);
    }


    /**
     * checks whether the note is within the specified date range.
     *
     * @param note note.
     * @param start start date.
     * @param end end date.
     * @return true or false.
     */
    private boolean isWithinDateRange(Note note, Date start, Date end) {
        long noteTime = note.getDate().getTime();
        return noteTime >= start.getTime() && noteTime <= end.getTime();
    }

    /**
     * checks whether the note contains stated keywords regardless of the register.
     *
     * @param note note.
     * @param keyWords keywords. arr of strings.
     * @return true or false.
     */
    private boolean containsKeyword(Note note, String[] keyWords) {
        String noteTitleLower = note.getTitle().toLowerCase();
        return Stream.of(keyWords)
                .map(String::toLowerCase)
                .anyMatch(noteTitleLower::contains);
    }

    /**
     * adds a note.
     *
     * @param title string.
     * @param text string.
     * @param currentDate date of creation.
     */
    public void add(String title, String text, Date currentDate) {
        notes.push(new Note(title, text, currentDate));
        try {
            writeToJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * removes all notes with provided title.
     *
     * @param title string.
     */
    public void delete(String title) {
        notes.removeIf(note -> note.getTitle().equals(title));
        try {
            writeToJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * uploads notes from the json.
     */
    private void readFromJson() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Note>>() {
        }.getType();
        File file = new File(jsonFile);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                // Convert JSON File to Java Object
                List<Note> noteList = gson.fromJson(reader, listType);

                // Create a Deque from the List
                notes = new ArrayDeque<>(noteList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            notes = new ArrayDeque<>();
        }
    }

    /**
     * writes notes to the json file.
     *
     * @throws IOException exception.
     */
    private void writeToJson() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(jsonFile);
        writer.write(gson.toJson(notes));
        writer.close();
    }
}
