package ru.kozorez;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class Json {
    private final String jsonFile = "config.json";

    public SetUpPizzeria parseJson() throws IOException {
        Gson gson = new Gson();
        SetUpPizzeria pizzeria = null;
        try (Reader reader = new FileReader(jsonFile)) {

            // Convert JSON File to Java Object
            pizzeria = gson.fromJson(reader, SetUpPizzeria.class);

            //System.out.println(pizzeria);

        } catch (IOException e) {
            System.out.println("Json file not found:" + e);
            throw new IOException();
        }
        return pizzeria;

    }

    public void writeJson(SetUpPizzeria pizzeria) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // Java objects to String
        String json = gson.toJson(pizzeria);

        //System.out.println(json);

        // Java objects to File
        try (FileWriter writer = new FileWriter("JSOOON.json")) {
            gson.toJson(json, writer);
        } catch (IOException e) {
            System.out.println("Error writing to json:" + e);
        }
    }
}
