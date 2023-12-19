package ru.nsu.kozorez;


import java.io.*;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Notebook {
    private Deque<Note> notes;// = new ArrayDeque<>();

    private final String jsonFile = "notebook.json";

    public Notebook() {
        readFromJson();
    }

    public void show() {
        for (Note note : notes) {
            System.out.println(note);
        }

    }

//    public void show(Date start, Date end, String[] keyWords) {
//        for (Note note : notes) {
//            if (note.getDate().getTime() >= start.getTime()
//                    && note.getDate().getTime() <= end.getTime()) {
//                for (String keyWord : keyWords) {
//                    if (note.getTitle().toLowerCase().contains(keyWord.toLowerCase())) {
//                        System.out.println(note);
//                        break;
//                    }
//                }
//            }
//        }
//    }
public void show(Date start, Date end, String[] keyWords) {
    Stream<Note> noteStream = notes.stream()
            .filter(note -> isWithinDateRange(note, start, end))
            .filter(note -> containsKeyword(note, keyWords));

    noteStream.forEach(System.out::println);
}

    private boolean isWithinDateRange(Note note, Date start, Date end) {
        long noteTime = note.getDate().getTime();
        return noteTime >= start.getTime() && noteTime <= end.getTime();
    }

    private boolean containsKeyword(Note note, String[] keyWords) {
        String noteTitleLower = note.getTitle().toLowerCase();
        return Stream.of(keyWords)
                .map(String::toLowerCase)
                .anyMatch(noteTitleLower::contains);
    }

    public void add(String title, String text, Date currentDate) {
        notes.push(new Note(title, text, currentDate));
        try {
            writeToJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String title) { //removes all notes with provided title
        notes.removeIf(note -> note.getTitle().equals(title));
    }

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

                // Now you can use the Deque 'notes'
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            notes = new ArrayDeque<>();
        }
    }

    private void writeToJson() throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(jsonFile);
        writer.write(gson.toJson(notes));
        writer.close();
    }
}
