package ru.kozorez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
/*        Settings settings = new Settings(700, 500);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), settings.getLENGTH(), settings.getHEIGHT());
        stage.setTitle("Hello!");
        stage.setScene(scene);
*//*        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);*//*
        stage.show();*/
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getRoot().requestFocus();
        stage.setTitle("Sankey!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}