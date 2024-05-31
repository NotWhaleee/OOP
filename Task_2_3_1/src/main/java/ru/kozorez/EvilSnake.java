package ru.kozorez;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.W;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class for the bot snake.
 */
public class EvilSnake extends Snake {

    private List<Double> distanceToFood;
    private final int gridSize;

    /**
     * initialize the snake.
     *
     * @param gridSize gridSize
     */
    public EvilSnake(int gridSize, double startX, double startY, double radius, Color color) {
        super(gridSize, startX, startY, radius, color);
        this.gridSize = gridSize;
    }

    /**
     * calculate distances to the food.
     *
     * @param foods list of foods
     */
    public void calcDistance(List<Circle> foods) {
        distanceToFood = new ArrayList<>();
        for (Circle food : foods) {
            double distance = Math.sqrt(Math.pow(food.getCenterX() - getHeadX(), 2)
                    + Math.pow(food.getCenterY() - getHeadY(), 2));
            distanceToFood.add(distance);
        }
    }

    /**
     * index of the closest food.
     *
     * @return index
     */
    private int findMinDistIndex() {
        int index = 0;
        double minDist = distanceToFood.get(0);
        for (int i = 1; i < distanceToFood.size(); i++) {
            if (distanceToFood.get(i) < minDist) {
                minDist = distanceToFood.get(i);
                index = i;
            }
        }
        return index;
    }

    /**
     * set new snakes direction of movement.
     *
     * @param foods list of foods
     */
    public void setDirection(List<Circle> foods) {
        if (distanceToFood == null) {
            return;
        }

        int minDistIndex = findMinDistIndex();
        Circle nearestFood = foods.get(minDistIndex);
        double inaccuracy = nearestFood.getRadius() * 0.75;

        double foodCenterX = nearestFood.getCenterX();
        double foodCenterY = nearestFood.getCenterY();

        double headX = getHeadX();
        double headY = getHeadY();

        if (headX + inaccuracy < foodCenterX) {
            setDirectionBasedOnX(D, W, headX + gridSize, headY,
                    !oppositeKeyCode(getDirection(), D));
        } else if (headX - inaccuracy > foodCenterX) {
            setDirectionBasedOnX(A, S, headX - gridSize, headY,
                    !oppositeKeyCode(getDirection(), A));
        }

        if (headY + inaccuracy < foodCenterY) {
            setDirectionBasedOnY(S, A, headX, headY + gridSize,
                    !oppositeKeyCode(getDirection(), S));
        } else if (headY - inaccuracy > foodCenterY) {
            setDirectionBasedOnY(W, D, headX, headY - gridSize,
                    !oppositeKeyCode(getDirection(), W));
        }
    }

    /**
     * set new snakes direction of movement on x.
     *
     * @param direction direction of the movement
     * @param perpendicularDirection perpendicular direction of the movement
     * @param newX new x coordinate
     * @param newY new y coordinate
     * @param oppositeDirection is moving in the opposite direction
     */
    private void setDirectionBasedOnX(KeyCode direction, KeyCode perpendicularDirection,
                                      double newX, double newY, boolean oppositeDirection) {
        if (oppositeDirection && !hitYourselfOnPoliceTurn(newX, newY, getHeadRadius())) {
            setDirection(direction);
        } else if (!hitYourselfOnPoliceTurn(getHeadX(), getHeadY() - gridSize, getHeadRadius())) {
            setDirection(perpendicularDirection);
        }
    }

    /**
     * set new snakes direction of movement on y.
     *
     * @param direction direction of the movement
     * @param perpendicularDirection perpendicular direction of the movement
     * @param newX new x coordinate
     * @param newY new y coordinate
     * @param oppositeDirection is moving in the opposite direction
     */
    private void setDirectionBasedOnY(KeyCode direction, KeyCode perpendicularDirection,
                                      double newX, double newY, boolean oppositeDirection) {
        if (oppositeDirection && !hitYourselfOnPoliceTurn(newX, newY, getHeadRadius())) {
            setDirection(direction);
        } else if (!hitYourselfOnPoliceTurn(getHeadX() - gridSize, getHeadY(), getHeadRadius())) {
            setDirection(perpendicularDirection);
        }
    }

    /**
     * checks if the snake would hit itself while turning around.
     *
     * @param headX x coordinate of the head
     * @param headY y coordinate of the head
     * @param headRadius head radius
     * @return whether the snake would hit itself while turning around or not
     */
    public boolean hitYourselfOnPoliceTurn(double headX, double headY, double headRadius) {
        for (int i = 1; i < getBody().size(); i++) {
            Circle segment = getBody().get(i);
            double inaccuracy = headRadius * 0.2;

            double distance = Math.sqrt(Math.pow(headX - segment.getCenterX(), 2)
                    + Math.pow(headY - segment.getCenterY(), 2));

            if (distance + inaccuracy < headRadius + segment.getRadius()) {
                return true;
            }
        }
        return false;
    }
}
