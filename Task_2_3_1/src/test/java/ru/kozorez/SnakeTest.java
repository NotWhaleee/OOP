package ru.kozorez;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * class for testing snakes' logic.
 */
class SnakeTest {

    private Snake snake;

    /**
     * Initializes a new instance of the Snake class before each test.
     */
    @BeforeEach
    void setUp() {
        snake = new Snake(15);
    }

    /**
     * Tests the constructor of the Snake class.
     * Verifies the initial state of the snake's body, direction, and size.
     */
    @Test
    void testConstructor() {
        List<Circle> body = snake.getBody();
        assertEquals(1, body.size());
        assertEquals(KeyCode.D, snake.getDirection());
        assertEquals(10, snake.getSize());
    }

    /**
     * Tests setting a new direction for the snake.
     * Verifies that the direction is set correctly.
     */
    @Test
    void testSetDirection() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false);

        snake.setDirection(event);

        assertEquals(KeyCode.W, snake.getDirection());
    }

    /**
     * Tests setting an opposite direction to the current direction.
     * Verifies that the opposite direction is ignored.
     */
    @Test
    void testSetOppositeDirection() {
        snake.setDirection(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false));
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.S, false, false, false, false);

        snake.setDirection(event);

        assertEquals(KeyCode.W, snake.getDirection());
    }

    /**
     * Tests the movement of the snake.
     * Verifies that the snake moves correctly without collision.
     */
    @Test
    void testMove() {
        snake.setDirection(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false));

        boolean collision = snake.move(200, 200);

        assertFalse(collision);
        assertEquals(100, snake.getHeadX());
        assertEquals(85, snake.getHeadY());
    }

    /**
     * Tests wall collision on the X-axis.
     * Verifies that the snake reacts correctly to wall collisions.
     */
    @Test
    void testWallCollisionX() {
        double newX = snake.WallCollisionX(5, 200);

        assertEquals(190, newX);

        newX = snake.WallCollisionX(205, 200);

        assertEquals(10, newX);
    }


    /**
     * Tests wall collision on the Y-axis.
     * Verifies that the snake reacts correctly to wall collisions.
     */
    @Test
    void testWallCollisionY() {
        double newY = snake.wallCollisionY(5, 200);

        assertEquals(190, newY);

        newY = snake.wallCollisionY(205, 200);

        assertEquals(10, newY);
    }

    /**
     * Tests collision detection with the snake's body.
     * Verifies that the snake does not collide with itself.
     */
    @Test
    void testStupidSnakeBodyCollision() {

        snake.getBody().add(new Circle(100, 100, 10));
        snake.getBody().add(new Circle(110, 100, 10));

        assertFalse(snake.stupidSnakeBodyCollision());
    }


    /**
     * Tests the growth of the snake.
     * Verifies that the snake grows by adding a new segment to its body.
     */
    @Test
    void testGrow() {
        Circle newSegment = snake.grow();
        List<Circle> body = snake.getBody();

        assertEquals(2, body.size());
        assertEquals(newSegment, body.getLast());
    }
}
