package ru.kozorez;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.W;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * class for snake logic.
 */
public class Snake {

    private List<Circle> body;
    private KeyCode direction;
    private final int gridSize;
    private int score = 0;
    private Color color;


    /**
     * initialize the snake.
     *
     * @param gridSize gridSize
     */
    public Snake(int gridSize, double startX, double startY, double radius, Color color) {
        this.body = new ArrayList<>();
        this.direction = D;
        this.gridSize = gridSize;
        this.color = color;
        this.body.add(new Circle(startX, startY, radius));
    }

    /**
     * get list of body parts.
     *
     * @return List of circles
     */
    public List<Circle> getBody() {
        return body;
    }

    /**
     * get x coord of the head.
     *
     * @return coordinate
     */
    public double getHeadX() {
        return body.get(0).getCenterX();
    }

    /**
     * get y coord of the head.
     *
     * @return coordinate
     */
    public double getHeadY() {
        return body.get(0).getCenterY();
    }

    /**
     * get radius of the head.
     *
     * @return double
     */
    public double getHeadRadius() {
        return body.get(0).getRadius();
    }

    /**
     * set direction of the snakes' movement.
     *
     * @param direction KeyEvent
     */
    public void setDirection(KeyEvent direction) {
        if (direction != null && !oppositeKeyCode(direction.getCode(), this.direction)) {
            this.direction = direction.getCode();
        }
    }

    /**
     * set direction of the snakes' movement.
     *
     * @param direction KeyCode
     */
    public void setDirection(KeyCode direction) {
        if (direction != null && !oppositeKeyCode(direction, this.direction)) {
            this.direction = direction;
        }
    }

    /**
     * check whether the keys are opposite or not.
     *
     * @param a key 1
     * @param b key 2
     * @return whether the keys are opposite or not
     */
    public boolean oppositeKeyCode(KeyCode a, KeyCode b) {
        if (a == A && b == D || b == A && a == D) {
            return true;
        }
        if (a == W && b == S || b == W && a == S) {
            return true;
        }
        return false;

    }

    /**
     * get radius of the snakes' circles.
     *
     * @return double
     */
    public double getSize() {
        return body.get(0).getRadius();
    }

    /**
     * start moving snake.
     *
     * @param maxWidth  max width of the window
     * @param maxHeight max height of the window
     * @return whether there was a collision or not
     */
    public boolean move(double maxWidth, double maxHeight, Snake enemy, boolean withBot) {
        double newX = body.get(0).getCenterX();
        double newY = body.get(0).getCenterY();

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
            default:
        }

        newX = wallCollisionX(newX, maxWidth);
        newY = wallCollisionY(newY, maxHeight);

        crawlBody();
        crawlHead(newX, newY);

        if (collisionWithItself()) {
            System.out.println("Game over!!!");
        }
        return collisionWithItself() || (withBot && collisionWithEnemy(enemy));
    }

    /**
     * Move the snake's body.
     */
    private void crawlBody() {
        for (int i = body.size() - 1; i > 0; i--) {
            body.get(i).setCenterX(body.get(i - 1).getCenterX());
            body.get(i).setCenterY(body.get(i - 1).getCenterY());
        }
    }

    /**
     * Move the snake's head.
     *
     * @param newX new x coord
     * @param newY new y coord
     */
    private void crawlHead(double newX, double newY) {
        body.get(0).setCenterX(newX);
        body.get(0).setCenterY(newY);
    }

    /**
     * teleport snake from the side to side.
     *
     * @param x        coordinate x
     * @param maxWidth max width of the window
     * @return new coordinate
     */
    double wallCollisionX(double x, double maxWidth) {
        if (x < body.get(0).getRadius()) {
            x = maxWidth - body.get(0).getRadius();
        } else if (x >= maxWidth) {
            x = body.get(0).getRadius();
        }
        return x;
    }

    /**
     * teleport snake from the top to the bottom and reverse.
     *
     * @param y         coordinate y
     * @param maxHeight max height of the window
     * @return new coordinate
     */
    double wallCollisionY(double y, double maxHeight) {
        if (y < body.get(0).getRadius()) {
            y = maxHeight - body.get(0).getRadius();
        } else if (y >= maxHeight) {
            y = body.get(0).getRadius();
        }
        return y;
    }


    /**
     * Check whether the snake's head intersects with any of its body segments or not.
     *
     * @return whether there was a collision or not
     */
    public boolean collisionWithItself() {
        Circle head = body.get(0);
        for (int i = 3; i < body.size(); i++) {
            Circle segment = body.get(i);
            double distance = Math.sqrt(Math.pow(head.getCenterX() - segment.getCenterX(), 2)
                    + Math.pow(head.getCenterY() - segment.getCenterY(), 2));
            if (distance < head.getRadius() + segment.getRadius()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the snake's head intersects with any of its body segments or not.
     *
     * @return whether there was a collision or not
     */
    public boolean collisionWithEnemy(Snake enemy) {
        Circle head = body.get(0);
        for (int i = 0; i < enemy.getBody().size(); i++) {
            Circle segment = enemy.getBody().get(i);
            double distance = Math.sqrt(Math.pow(head.getCenterX() - segment.getCenterX(), 2)
                    + Math.pow(head.getCenterY() - segment.getCenterY(), 2));
            if (distance < head.getRadius() + segment.getRadius()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Add a new segment to the end of the snake's body.
     *
     * @return circle
     */
    public Circle grow(Color color) {
        Circle lastSegment = body.get(body.size() - 1);
        double newX = lastSegment.getCenterX();
        double newY = lastSegment.getCenterY();

        Circle newSegment = new Circle(newX, newY, lastSegment.getRadius(), color);
        newSegment.setStroke(Color.BLACK);

        body.add(newSegment);
        return newSegment;
    }

    /**
     * get the direction of movement.
     *
     * @return KeyCode
     */
    public KeyCode getDirection() {
        return direction;
    }

    /**
     * get snakes score counter.
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * increments the score.
     */
    public void incrementScore() {
        this.score++;
    }

    /**
     * get snakes color.
     *
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * set snakes color.
     *
     * @param color Color
     */
    public void setColor(Color color) {
        this.color = color;
    }
}