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
    private Circle playerHead;

    @FXML
    private Circle evilSnakeHead;

    @FXML
    private Label playerScoreTxt;

    @FXML
    private Label evilScoreTxt;

    private List<Circle> foods = new ArrayList<>();

    private final int gridSize = 15;
    private Snake player;
    private EvilSnake evilSnake;
    int foodCount;
    double defaultWidth;
    double defaultHeight;

    int targetScore = 15;
    private boolean withBot = true;

    private double startPlayerX = 100;
    private double startPlayerY = 100;
    private final double radiusPlayer = 10;
    private Color playerColor = Color.LIGHTGREEN;

    private double startEvilX = 0;
    private double startEvilY = 0;
    private final double radiusEvil = 10;
    private Color evilColor = Color.RED;

    private Color foodColor = Color.LIGHTYELLOW;


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
        player = new Snake(gridSize, startPlayerX, startPlayerY, radiusPlayer, playerColor);
        if (withBot) {
            evilSnake = new EvilSnake(gridSize, startEvilX, startEvilY, radiusEvil, evilColor);
        }
        evilSnakeHead.setVisible(withBot);
        evilScoreTxt.setVisible(withBot);
        final long[] lastUpdate = {0};
        foodCount = 5;
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
    private void eatFood(Snake snake, Circle snakeHead) {
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
                plane.getChildren().add(snake.grow(snake.getColor()));
                plane.getChildren().remove(foods.remove(i));
                spawnFood();
                snake.incrementScore();
                if (snake.getScore() == targetScore) {
                    gameOver();
                }
            }
        }
    }

    /**
     * check if the food has spawned into the snake.
     *
     * @param food circle
     * @return whether the food has spawned into the snake or not
     */
    private boolean checkCollisionWithFood(Circle food, Snake snake) {
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
                player.setDirection(event);
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
            food = new Circle(x, y, player.getSize(), foodColor);
        } while (checkCollisionWithFood(food, player)
                || (withBot && checkCollisionWithFood(food, evilSnake)));

        food.setStroke(Color.BLACK);
        foods.add(food);
        plane.getChildren().add(foods.get(foods.size() - 1));

        if (withBot) {
            evilSnake.calcDistance(foods);
        }
    }

    /**
     * runs the game.
     */
    private void update() {
        playerScoreTxt.setText("Your Score: " + player.getScore());
        if (withBot) {
            evilScoreTxt.setText("Opponent Score: " + evilSnake.getScore());
            evilScoreTxt.setLayoutX(plane.getWidth() - evilScoreTxt.getWidth() * 1.1);
        }

        if (foodCount > foods.size() || foods.isEmpty()) {
            spawnFood();
        }
        if (foodCount < foods.size()) {
            System.out.println(foodCount + " " + foods.size());
            foods.get(foods.size() - 1).setRadius(0);
            foods.remove(foods.size() - 1);
        }
        if (player.move(plane.getWidth(), plane.getHeight(), evilSnake, withBot)) {
            //gameOver();
            respawnSnake(false, player, playerHead, playerScoreTxt,
                    startPlayerX, startPlayerY, radiusPlayer);
        }
        if (withBot && evilSnake.move(plane.getWidth(), plane.getHeight(), player, withBot)) {
            //gameOver();
            respawnSnake(true, evilSnake, evilSnakeHead, evilScoreTxt,
                    startEvilX, startEvilY, radiusEvil);
            evilSnake.calcDistance(foods);
        }

        eatFood(player, playerHead);
        if (withBot && evilSnake != null) {
            evilSnake.setDirection(foods);
            eatFood(evilSnake, evilSnakeHead);
        }


        playerHead.setCenterX(player.getHeadX());
        playerHead.setCenterY(player.getHeadY());

        if (withBot) {
            evilSnakeHead.setCenterX(evilSnake.getHeadX());
            evilSnakeHead.setCenterY(evilSnake.getHeadY());
        }

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
            popupController.setScore(player.getScore());
            popupController.controller = this;

            Stage newStage = new Stage();
            newStage.setMinHeight(defaultHeight);
            newStage.setMinWidth(defaultWidth);
            newStage.setScene(new Scene(root));
            newStage.setTitle("Settings");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * restarts the game.
     */
    public void restartGame() {
        plane.getChildren().clear();

        respawnSnake(false, player, playerHead, playerScoreTxt,
                startPlayerX, startPlayerY, radiusPlayer);
        if (withBot) {
            respawnSnake(true, evilSnake, evilSnakeHead, evilScoreTxt,
                    startEvilX, startEvilY, radiusEvil);
        }

        evilScoreTxt.setVisible(withBot);
        foods = new ArrayList<>();
    }

    private void respawnSnake(boolean evil, Snake snake, Circle snakeHead, Label scoreTxt,
                              double startX, double startY, double radius) {
        plane.getChildren().removeAll(snake.getBody());
        plane.getChildren().removeAll(snakeHead);
        plane.getChildren().removeAll(scoreTxt);

        if (evil) {
            evilSnake = new EvilSnake(gridSize, startX, startY, radius, evilColor);
        } else {
            player = new Snake(gridSize, startX, startY, radius, playerColor);
        }

        plane.getChildren().add(snakeHead);
        plane.getChildren().add(scoreTxt);
    }

    public boolean isWithBot() {
        return withBot;
    }

    public void setWithBot(boolean withBot) {
        this.withBot = withBot;
    }
}