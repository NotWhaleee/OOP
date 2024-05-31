package ru.kozorez;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

/**
 * class for testing bot snake.
 */
public class EvilSnakeTest {

    @Test
    public void testEvilSnakeInitialization() {
        EvilSnake snake = new EvilSnake(10, 50, 50, 5, Color.GREEN);
        assertNotNull(snake.getBody());
        assertEquals(1, snake.getBody().size());
        assertEquals(KeyCode.D, snake.getDirection());
        assertEquals(Color.GREEN, snake.getColor());
    }

    @Test
    public void testHitYourselfOnPoliceTurn() {
        EvilSnake snake = new EvilSnake(10, 50, 50, 5, Color.GREEN);
        assertFalse(snake.hitYourselfOnPoliceTurn(55, 55, 5));
        snake.grow(Color.GREEN);
        assertTrue(snake.hitYourselfOnPoliceTurn(55, 55, 5));
    }
}
