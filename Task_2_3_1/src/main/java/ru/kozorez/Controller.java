package ru.kozorez;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    AnimationTimer gameLoop;

    @FXML
    private AnchorPane plane;

    @FXML
    private Circle snake;

    @FXML
    private Text score;

    //private double accelerationTime = 0;
    private int gameTime = 0;
    private int scoreCounter = 0;

    private Snake snakeComponent;
    Bounds bounds;
    //private ObstaclesHandler obstaclesHandler;


    ArrayList<Rectangle> obstacles = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        double speed = 0.1;
        snakeComponent = new Snake(snake, speed);
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        //load();

        gameLoop.start();
    }

    @FXML
    void pressed(KeyEvent event) {

        switch (event.getCode()) {
            case W:
            case A:
            case S:
            case D:
                snakeComponent.key = event;
                break;
        }

    }

    private void teleportSnake() {

        bounds = plane.getBoundsInLocal();

        //System.out.println(snake.getCenterY() + " " + (bounds.getCenterY()) + " "  + (bounds.getMaxY()));
        //if(bounds.getMinY() < 0 || bounds.getMaxY() > bounds.getCenterY()
        ///*snake.getCenterY() >= (bounds.getMaxY() - snake.getRadius())*/){
        System.out.println(snake.getCenterY() + " " + (bounds.getCenterY()) + " " + (bounds.getMaxY()));
        if (snake.getCenterY() < 0) {

            snake.setCenterY(bounds.getCenterY() - snake.getRadius());
            snake.setTranslateY(bounds.getCenterY() - snake.getRadius());
        }
        if (snake.getCenterY() + snake.getRadius() / 2 >= bounds.getCenterY()) {
            snake.setCenterY(0);
            snake.setTranslateY(0);
        }
    }

    //Called every game frame
    private void update() {
        gameTime++;
        snakeComponent.move();
        teleportSnake();

        //snake.moveBirdY(yDelta * accelerationTime);
/*
        if(pointChecker(obstacles, bird)){
            scoreCounter++;
            score.setText(String.valueOf(scoreCounter));
        }

        obstaclesHandler.moveObstacles(obstacles);
        if(gameTime % 500 == 0){
            obstacles.addAll(obstaclesHandler.createObstacles());
        }

        if(birdComponent.isBirdDead(obstacles, plane)){
            resetGame();
        }*/
    }

/*    //Everything called once, at the game start
    private void load(){
        obstacles.addAll(obstaclesHandler.createObstacles());
    }

    private void resetGame(){
        bird.setY(0);
        plane.getChildren().removeAll(obstacles);
        obstacles.clear();
        gameTime = 0;
        accelerationTime = 0;
        scoreCounter = 0;
        score.setText(String.valueOf(scoreCounter));
    }*/


    private boolean pointChecker(ArrayList<Rectangle> obstacles, Rectangle bird) {
        for (Rectangle obstacle : obstacles) {
            int birdPositionX = (int) (bird.getLayoutX() + bird.getX());
            if (((int) (obstacle.getLayoutX() + obstacle.getX()) == birdPositionX)) {
                return true;
            }
        }
        return false;
    }
}