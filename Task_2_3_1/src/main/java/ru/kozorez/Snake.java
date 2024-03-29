package ru.kozorez;

//import com.almasb.fxgl.physics.CollisionHandler;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;


public class Snake {

    private Circle snake;
    public double speed;

    public KeyEvent key;
    CollisionHandler collisionHandler = new CollisionHandler();

    public Snake(Circle snake, double speed) {
        this.snake = snake;
        this.speed = speed;
    }

    public void move() {
        switch (key.getCode()) {
            case W:
                goUP();
                break;
            case A:
                goLeft();
                break;
            case S:
                goDown();
                break;
            case D:
                goRight();
                break;
        }
//        double movement = -jumpHeight;
//        if(bird.getLayoutY() + bird.getY() <= jumpHeight){
//            movement = -(bird.getLayoutY() + bird.getY());
//        }
//
//        moveBirdY(movement);
    }



    private void goUP() {
        snake.setTranslateY(snake.getCenterY() - speed);
        snake.setCenterY(snake.getCenterY() - speed);
    }
    private void goDown() {
        snake.setTranslateY(snake.getCenterY() + speed);
        snake.setCenterY(snake.getCenterY() + speed);
    }
    private void goRight() {
        snake.setTranslateX(snake.getCenterX() + speed);
        snake.setCenterX(snake.getCenterX() + speed);
    }
    private void goLeft() {
        snake.setTranslateX(snake.getCenterX() - speed);
        snake.setCenterX(snake.getCenterX() - speed);
    }


   /* private void loopX(){
        if(snake.getLayoutY())
    }

    public void moveBirdY(double positionChange){
        bird.setY(bird.getY() + positionChange);
    }

    public boolean isBirdDead(ArrayList<Circle> obstacles, AnchorPane plane){
        double birdY = bird.getLayoutY() + bird.getY();

        if(collisionHandler.collisionDetection(obstacles, bird)){
            return  true;
        }

        return birdY >= plane.getHeight();
    }*/
}