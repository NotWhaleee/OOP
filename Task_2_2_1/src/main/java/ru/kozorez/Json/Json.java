package ru.kozorez.Json;

import com.google.gson.Gson;
import ru.kozorez.Pizzeria.SetUpPizzeria;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;


/**
 * Class for operations with config json.
 */
public class Json {


    private final String jsonFile;

    /**
     * initialize json file.
     *
     * @param jsonFile string
     */
    public Json(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    /**
     * set up pizzeria based on config file.
     *
     * @return pizzeria
     * @throws IOException error reading config file
     */
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

    /**
     * writes to json config file.
     *
     * @param pizzeria pizzeria
     */
    public void writeJson(SetUpPizzeria pizzeria) {
        Gson gson = new Gson();
        String json = gson.toJson(pizzeria);

        // Java objects to File
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Error writing to json:" + e);
        }
    }
}
