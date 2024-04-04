package ru.kozorez;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    AnimationTimer gameLoop;

    @FXML
    private AnchorPane plane;

    @FXML
    private Circle snakeHead;

    @FXML
    private Label scoreText;

    @FXML
    private Label gameOver;

    private List<Circle> foods = new ArrayList<>();
    @FXML
    private Text score;

    //private double accelerationTime = 0;
    private int gameTime = 0;
    private int scoreCounter = 0;
    private final int gridSize = 15;
    private Snake snake;

    private URL url;
    private ResourceBundle resourceBundle;


    ArrayList<Rectangle> obstacles = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.url = url;
        this.resourceBundle = resourceBundle;
        snake = new Snake(gridSize);
        final long[] lastUpdate = {0};
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate[0] >= 30_000_000) { // Adjust the value as per your requirement
                    update();
                    lastUpdate[0] = now;
                }
            }
        };

        gameLoop.start();
    }

    private void eatFood() {
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i) == null) {
                continue;
            }
            if (foods.get(i).getCenterY() > plane.getHeight()
                    || foods.get(i).getCenterX() > plane.getWidth()) {
                plane.getChildren().remove(foods.remove(i));
                spawnFood();
            }
            if (snakeHead.getBoundsInParent().intersects(foods.get(i).getBoundsInParent())) {
                plane.getChildren().add(snake.grow());
                plane.getChildren().remove(foods.remove(i));
                spawnFood();
                scoreCounter++;
            }
        }
    }

    private boolean checkCollisionWithFood(Circle food) {
        for (int i = 0; i < snake.getBody().size(); i++) {
            if (snake.getBody().get(i).getBoundsInParent().intersects(food.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void pressed(KeyEvent event) {

        switch (event.getCode()) {
            case W:
            case A:
            case S:
            case D:
                snake.setDirection(event);
                //snake.direction = event;
                break;
            case SPACE:
                restartGame();
                break;
        }
    }

    private void spawnFood() {

        Circle food;
        do {
            double x = Math.random() * plane.getWidth();
            double y = Math.random() * plane.getHeight();
            food = new Circle(x, y, snake.getSize(), Color.GREEN);
        } while (checkCollisionWithFood(food));

        food.setStroke(Color.BLACK);
        foods.addLast(food); // Adjust the size of the food circle
        plane.getChildren().add(foods.getLast());
    }

    //Called every game frame
    private void update() {
        scoreText.setText("Score: " + scoreCounter);
        if(foods.isEmpty()){
            spawnFood();
        }
        if (scoreCounter == 0) {
            spawnFood();
        }
        //System.out.println(plane.getWidth() + " " + plane.getHeight());
        if(snake.move(plane.getWidth(), plane.getHeight())){
            gameOver();
        }
        eatFood();
        snakeHead.setCenterX(snake.getHeadX());
        snakeHead.setCenterY(snake.getHeadY());
    }

    private void gameOver() {
        // Display game over window
        gameOver.setText("GAME OVER!\nScore: " + scoreCounter);
        gameLoop.stop();
    }

    private void restartGame(){
        scoreCounter = 0;

        plane.getChildren().removeAll(snake.getBody());
        for (int i = 0; i < snake.getBody().size(); i++) {
            snake.getBody().removeLast();
        }

        plane.getChildren().removeAll(foods);
        for (int i = 0; i <foods.size(); i++) {
            foods.removeLast();
        }

        gameLoop.stop();
        gameOver.setText("");
        initialize(url, resourceBundle);
    }



}