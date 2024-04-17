package ru.kozorez;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * snake application.
 */
public class Application extends javafx.application.Application {

    /**
     * starts the snake application.
     *
     * @param stage the primary stage for this application
     * @throws IOException exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getRoot().requestFocus();
        stage.setTitle("Sankey!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main.
     *
     * @param args no args
     */
    public static void main(String[] args) {
        launch();
    }
}