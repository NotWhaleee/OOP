package ru.kozorez;

//import com.almasb.fxgl.physics.CollisionHandler;

import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.W;


public class Snake {

    private List<Circle> body;
    private long speed;

    //public KeyEvent direction;
    private KeyCode direction;

    private int gridSize;
    private int smoothness;

    public Snake(int gridSize) {
        this.body = new ArrayList<>();
        //this.direction = new KeyEvent(D); // Start moving right by default
        this.direction = D;
        this.gridSize = gridSize;
        this.body.add(new Circle(100, 100, 10));
    }

    public List<Circle> getBody() {
        return body;
    }

    public double getHeadX() {
        return body.getFirst().getCenterX();
    }

    public double getHeadY() {
        return body.getFirst().getCenterY();
    }

    public void setDirection(KeyEvent direction) {
        if (direction != null && !oppositeKeyCode(direction.getCode(), this.direction)) {
            this.direction = direction.getCode();
        }
    }

    private boolean oppositeKeyCode(KeyCode a, KeyCode b) {
        if (a == A && b == D || b == A && a == D) {
            return true;
        }
        if (a == W && b == S || b == W && a == S) {
            return true;
        }
        return false;

    }

    public double getSize() {
        return body.getFirst().getRadius();
    }

    public boolean move(double maxWidth, double maxHeight) {
        double newX = body.getFirst().getCenterX();
        double newY = body.getFirst().getCenterY();

        switch (direction) {
            case W:
                //goUP();
                newY -= gridSize;

                break;
            case A:
                //goLeft();
                newX -= gridSize;

                break;
            case S:
                //goDown();
                newY += gridSize;

                break;
            case D:
                //goRight();
                newX += gridSize;

                break;
        }

        newX = WallCollisionX(newX, maxWidth);
        newY = wallCollisionY(newY, maxHeight);


        crawlBody();
        crawlHead(newX, newY);
        if (stupidSnakeBodyCollision()) {
            System.out.println("Game over!!!");
        }
        return stupidSnakeBodyCollision();


    }

    private void crawlBody() {
        for (int i = body.size() - 1; i > 0; i--) {
            body.get(i).setCenterX(body.get(i - 1).getCenterX());
            body.get(i).setCenterY(body.get(i - 1).getCenterY());
        }
    }

    private void crawlHead(double newX, double newY) {
        // Move the snake's head
        body.getFirst().setCenterX(newX);
        body.getFirst().setCenterY(newY);
    }

    private double WallCollisionX(double x, double maxWidth) {
        // Wrap-around behavior for boundaries
        if (x < 0) {
            x = maxWidth - gridSize; // Move to the right side of the board
        } else if (x >= maxWidth) {
            x = 0; // Move to the left side of the board
        }
        return x;


    }

    private double wallCollisionY(double y, double maxHeight) {
        if (y < 0) {
            y = maxHeight - gridSize; // Move to the bottom of the board
        } else if (y >= maxHeight) {
            y = 0; // Move to the top of the board
        }
        return y;
    }

    // Method to calculate the angle of the last segment relative to the second-to-last segment
/*    private double getAngle() {
        Circle lastSegment = body.get(body.size() - 1);
        Circle secondLastSegment = body.get(body.size() - 2);
        double deltaX = lastSegment.getCenterX() - secondLastSegment.getCenterX();
        double deltaY = lastSegment.getCenterY() - secondLastSegment.getCenterY();
        return Math.toDegrees(Math.atan2(deltaY, deltaX));
    }*/

    public boolean stupidSnakeBodyCollision() {
        // Check if the snake's head intersects with any of its body segments
        Circle head = body.getFirst();
        for (int i = 3; i < body.size(); i++) {
            Circle segment = body.get(i);
            double distance = Math.sqrt(Math.pow(head.getCenterX() - segment.getCenterX(), 2)
                    + Math.pow(head.getCenterY() - segment.getCenterY(), 2));
            if (distance < head.getRadius() + segment.getRadius()) {
                return true; // Collision detected
            }
        }
        return false; // No collision
    }


    public Circle grow() {
        // Add a new segment to the end of the snake's body
        Circle lastSegment = body.getLast();
        double newX = lastSegment.getCenterX() - gridSize;
        double newY = lastSegment.getCenterY() - gridSize;
        //double newX = lastSegment.getCenterX() - (gridSize * Math.cos(Math.toRadians(getAngle())));
        //double newY = lastSegment.getCenterY() - (gridSize * Math.sin(Math.toRadians(getAngle())));
        Circle newSegment = new Circle(newX, newY, lastSegment.getRadius(), Color.RED);
        newSegment.setStroke(Color.BLACK);

        body.add(newSegment);
        return newSegment;
    }
}