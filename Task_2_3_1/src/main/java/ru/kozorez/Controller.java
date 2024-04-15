package ru.kozorez;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


/**
 * Controller class.
 */
public class Controller implements Initializable {


    AnimationTimer gameLoop;

    @FXML
    private AnchorPane plane;

    @FXML
    private Circle snakeHead;

    @FXML
    private Label scoreText;

    private List<Circle> foods = new ArrayList<>();

    private int scoreCounter = 0;
    private final int gridSize = 15;
    private Snake snake;
    int foodCount;
    double defaultWidth;
    double defaultHeight;

    /**
     * initialize the game.
     *
     * @param url            url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defaultHeight = plane.getHeight();
        defaultWidth = plane.getWidth();
        snake = new Snake(gridSize);
        final long[] lastUpdate = {0};
        foodCount = 1;
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate[0] >= 30_000_000) {
                    update();
                    lastUpdate[0] = now;
                }
            }
        };
        gameLoop.start();
    }

    /**
     * set the amount of food.
     *
     * @param foodCount int
     */
    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    /**
     * check if the snake can eat some food.
     */
    private void eatFood() {
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i) == null) {
                continue;
            }
            //check foods collision with x and y borders
            if (plane.getWidth() <= foods.get(i).getRadius() + foods.get(i).getCenterX()
                    || foods.get(i).getRadius() >= foods.get(i).getCenterX()
                    || plane.getHeight() <= foods.get(i).getRadius() + foods.get(i).getCenterY()
                    || foods.get(i).getRadius() >= foods.get(i).getCenterY()) {
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

    /**
     * check if the food has spawned into the snake.
     *
     * @param food circle
     * @return whether the food has spawned into the snake or not
     */
    private boolean checkCollisionWithFood(Circle food) {
        for (int i = 0; i < snake.getBody().size(); i++) {
            if (snake.getBody().get(i).getBoundsInParent().intersects(food.getBoundsInParent())) {
                return true;
            }

            if (plane.getWidth() <= food.getRadius() + food.getCenterX()
                    || food.getRadius() >= food.getCenterX()) {
                return true;
            }
            if (plane.getHeight() <= food.getRadius() + food.getCenterY()
                    || food.getRadius() >= food.getCenterY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * movement from the keyboard.
     *
     * @param event pressing the key
     */
    @FXML
    void pressed(KeyEvent event) {

        switch (event.getCode()) {
            case W:
            case A:
            case S:
            case D:
                snake.setDirection(event);
                break;
            case SPACE:
                restartGame();
                break;
            default:
        }
    }

    /**
     * spawn 1 circle of food.
     */
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

    /**
     * runs the game.
     */
    private void update() {
        scoreText.setText("Score: " + scoreCounter);

        if (foodCount > foods.size() || foods.isEmpty()) {
            spawnFood();
        }
        if (foodCount < foods.size()) {
            System.out.println(foodCount + " " + foods.size());
            foods.getLast().setRadius(0);
            foods.removeLast();
        }
        if (snake.move(plane.getWidth(), plane.getHeight())) {
            gameOver();
        }
        eatFood();
        snakeHead.setCenterX(snake.getHeadX());
        snakeHead.setCenterY(snake.getHeadY());
    }

    /**
     * game over.
     */
    private void gameOver() {
        gameLoop.stop();
        showPopup();
    }

    /**
     * shows game over popup window.
     */
    private void showPopup() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameOver.fxml"));
        Parent root;
        try {
            root = loader.load();
            PopupController popupController = loader.getController();
            popupController.setScore(scoreCounter);
            popupController.controller = this;

            Stage newStage = new Stage();
            newStage.setMinHeight(defaultHeight);
            newStage.setMinWidth(defaultWidth);
            newStage.setScene(new Scene(root));
            newStage.setTitle("Game Over!");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * restarts the game.
     */
    public void restartGame() {
        scoreCounter = 0;
        plane.getChildren().removeAll(snake.getBody());
        plane.getChildren().removeAll(foods);
        snake = new Snake(gridSize);
        foods = new ArrayList<>();
    }
}