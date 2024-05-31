package ru.kozorez;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the snake.
 */
public class SnakeTest {

    @Test
    public void testSnakeInitialization() {
        Snake snake = new Snake(10, 50, 50, 5, Color.GREEN);
        assertNotNull(snake.getBody());
        assertEquals(1, snake.getBody().size());
        assertEquals(KeyCode.D, snake.getDirection());
        assertEquals(0, snake.getScore());
        assertEquals(Color.GREEN, snake.getColor());
    }

    @Test
    public void testSnakeMovement() {
        Snake snake = new Snake(10, 50, 50, 5, Color.GREEN);
        snake.move(100, 100, null, false);
        assertNotEquals(50, snake.getHeadX());
        assertEquals(50, snake.getHeadY());
    }

    @Test
    public void testSnakeDirectionChange() {
        Snake snake = new Snake(10, 50, 50, 5, Color.GREEN);
        snake.setDirection(KeyCode.S);
        assertEquals(KeyCode.S, snake.getDirection());
    }

    @Test
    public void testSnakeGrow() {
        Snake snake = new Snake(10, 50, 50, 5, Color.GREEN);
        Circle oldTail = snake.getBody().get(snake.getBody().size() - 1);
        Circle newSegment = snake.grow(Color.GREEN);
        assertEquals(oldTail.getCenterX(), newSegment.getCenterX());
        assertEquals(oldTail.getCenterY(), newSegment.getCenterY());
    }

    @Test
    public void testWallCollisionX() {
        Snake snake = new Snake(10, 50, 50, 5, Color.GREEN);
        double newX = snake.wallCollisionX(105, 100);
        assertEquals(5, newX);
    }

    @Test
    public void testWallCollisionY() {
        Snake snake = new Snake(10, 50, 50, 5, Color.GREEN);
        double newY = snake.wallCollisionY(105, 100);
        assertEquals(5, newY);
    }

    @Test
    public void testOppositeKeyCode() {
        Snake snake = new Snake(10, 50, 50, 5, Color.GREEN);
        assertTrue(snake.oppositeKeyCode(KeyCode.A, KeyCode.D));
        assertFalse(snake.oppositeKeyCode(KeyCode.A, KeyCode.W));
    }

    @Test
    public void testCollisionWithItself() {
        Snake snake = new Snake(10, 50, 50, 5, Color.GREEN);
        assertFalse(snake.collisionWithItself());
        // Assuming snake is long enough to collide with itself
        snake.grow(Color.GREEN);
        snake.grow(Color.GREEN);
        snake.grow(Color.GREEN);
        assertTrue(snake.collisionWithItself());
    }

    @Test
    public void testCollisionWithEnemyFalse() {
        Snake snake1 = new Snake(10, 50, 50, 5, Color.GREEN);
        Snake snake2 = new Snake(10, 100, 100, 5, Color.RED);
        assertFalse(snake1.collisionWithEnemy(snake2));
    }

    @Test
    public void testCollisionWithEnemyTrue() {
        Snake snake1 = new Snake(10, 50, 50, 5, Color.GREEN);
        Snake snake2 = new Snake(10, 50, 50, 5, Color.RED);
        assertTrue(snake1.collisionWithEnemy(snake2));
    }
}
