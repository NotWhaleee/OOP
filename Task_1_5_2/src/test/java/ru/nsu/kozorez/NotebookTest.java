package ru.nsu.kozorez;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Notebook test.
 */
public class NotebookTest {

    /**
     * deletes the json file.
     */
    void deleteJson() {
        File myObj = new File("notebook.json");
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    /**
     * reads notes from the json file.
     *
     * @return deque of notes.
     */
    Deque<Note> readFromJson() {
        Deque<Note> notes = null;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Note>>() {
        }.getType();
        File file = new File("notebook.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                // Convert JSON File to Java Object
                List<Note> noteList = gson.fromJson(reader, listType);

                // Create a Deque from the List
                notes = new ArrayDeque<>(noteList);

                // Now you can use the Deque 'notes'
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            notes = new ArrayDeque<>();
        }
        return notes;
    }

    @Test
    void testAddNotes() {
        deleteJson();

        Date currentDate = new Date();
        DateFormat dform = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        Notebook myDiarDiary = new Notebook();
        myDiarDiary.add("Title", "Some text", currentDate);
        myDiarDiary.add("Title2", "Some text2", currentDate);

        myDiarDiary.show();
        myDiarDiary.show(currentDate, currentDate, new String[]{"Title"});

        String expected = "Title: Title2\n"
                + "Creation date: " + dform.format(currentDate) + "\n"
                + "Some text2\n"
                + "Title: Title\n"
                + "Creation date: " + dform.format(currentDate) + "\n"
                + "Some text\n";

        assertEquals(expected, myDiarDiary.toString());


    }

    @Test
    void testDelete() {
        deleteJson();

        Date currentDate = new Date();
        DateFormat dform = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        Notebook myDiarDiary = new Notebook();
        myDiarDiary.add("Title", "Some text", currentDate);
        myDiarDiary.add("Title2", "Some text2", currentDate);

        myDiarDiary.delete("Title2");

        String expected = "Title: Title\n"
                + "Creation date: " + dform.format(currentDate) + "\n"
                + "Some text\n";

        assertEquals(expected, myDiarDiary.toString());
    }

    @Test
    void testDeleteUnexistingNote() {
        deleteJson();

        Date currentDate = new Date();
        DateFormat dform = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        Notebook myDiarDiary = new Notebook();
        myDiarDiary.add("Title", "Some text", currentDate);
        myDiarDiary.add("Title2", "Some text2", currentDate);

        myDiarDiary.delete("Title3");

        String expected = "Title: Title2\n"
                + "Creation date: " + dform.format(currentDate) + "\n"
                + "Some text2\n"
                + "Title: Title\n"
                + "Creation date: " + dform.format(currentDate) + "\n"
                + "Some text\n";

        assertEquals(expected, myDiarDiary.toString());
    }

    @Test
    void testJson() {
        deleteJson();

        Date currentDate = new Date();
        DateFormat dform = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        Notebook myDiarDiary = new Notebook();
        myDiarDiary.add("Title", "Some text", currentDate);

        Deque<Note> notes;
        notes = readFromJson();

        String expected = "[Title: Title\n"
                + "Creation date: " + dform.format(currentDate) + "\n"
                + "Some text\n]";

        assertEquals(expected, notes.toString());
    }

    @Test
    void testAddMain() throws IOException {
        deleteJson();
        Date currentDate = new Date();
        Notebook myDiarDiary = new Notebook();
        myDiarDiary.add("Title", "Some text", currentDate);

        Main main = new Main();
        main.parseArgs(new String[]{"-add", "TitleFromMain", "SomeeeeTexxttt"});
        main.handleArgs();

        Deque<Note> notes;

        notes = readFromJson();

        assertEquals(2, notes.size());
    }
}