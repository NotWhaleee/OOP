package ru.kozorez;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * snake application
 */
public class Application extends javafx.application.Application {

    /**
     * starts the snake application.
     *
      * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        scene.getRoot().requestFocus();
        stage.setTitle("Sankey!");
        /*Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);*/
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