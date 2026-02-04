package com.autodrive.simulation.model;

import com.autodrive.simulation.model.Field;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarTest {

    @Test
    void executeShouldTurnLeftAndRightAndMoveForwardWithinField() {
        Field field = new Field(5, 5);
        Car car = new Car("A", 1, 1, Direction.N, "LFRF");

        // Initial facing north at (1,1)
        car.execute('L', field); // turn left -> W
        assertEquals(Direction.W, car.getDirection());

        car.execute('F', field); // move west -> (0,1)
        assertEquals(0, car.getX());
        assertEquals(1, car.getY());

        car.execute('R', field); // turn right -> N
        assertEquals(Direction.N, car.getDirection());

        car.execute('F', field); // move north -> (0,2)
        assertEquals(0, car.getX());
        assertEquals(2, car.getY());
    }

    @Test
    void executeShouldIgnoreForwardMoveOutsideField() {
        Field field = new Field(5, 5);
        Car car = new Car("A", 0, 0, Direction.S, "F");

        car.execute('F', field); // would go to (0,-1) which is outside

        // Position should remain unchanged
        assertEquals(0, car.getX());
        assertEquals(0, car.getY());
    }

    @Test
    void positionKeyShouldMatchCurrentCoordinates() {
        Field field = new Field(5, 5);
        Car car = new Car("A", 2, 3, Direction.N, "F");

        assertEquals("2,3", car.positionKey());

        car.execute('F', field); // move to (2,4)
        assertEquals("2,4", car.positionKey());
    }

    @Test
    void deactivateShouldMarkCarInactive() {
        Field field = new Field(5, 5);
        Car car = new Car("A", 1, 1, Direction.N, "F");

        car.deactivate();
        assertTrue(!car.isActive());

        // After deactivation, execute should not move the car
        car.execute('F', field);
        assertEquals(1, car.getX());
        assertEquals(1, car.getY());
    }
}

