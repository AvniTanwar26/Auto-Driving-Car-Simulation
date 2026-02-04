package com.autodrive.simulation.service;

import com.autodrive.simulation.field.Field;
import com.autodrive.simulation.model.Car;
import com.autodrive.simulation.model.Direction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationServiceTest {

    private final SimulationService simulationService = new SimulationServiceImpl();

    @Test
    void runShouldReturnNullWhenNoCars() {
        Field field = new Field(10, 10);
        List<Car> emptyCars = new ArrayList<>();

        CollisionResult result = simulationService.run(field, emptyCars);

        assertNull(result);
    }

    @Test
    void singleCarScenarioShouldMatchExpectedFinalPosition() {
        Field field = new Field(10, 10);
        Car carA = new Car("A", 1, 2, Direction.N, "FFRFFFFRRL");
        List<Car> cars = new ArrayList<>();
        cars.add(carA);

        CollisionResult result = simulationService.run(field, cars);

        // No collision expected
        assertNull(result);

        // Final position and direction should match the scenario description
        assertEquals(5, carA.getX());
        assertEquals(4, carA.getY());
        assertEquals(Direction.S, carA.getDirection());
    }

    @Test
    void twoCarScenarioShouldDetectCollisionAtExpectedStepAndPosition() {
        Field field = new Field(10, 10);

        Car carA = new Car("A", 1, 2, Direction.N, "FFRFFFFRRL");
        Car carB = new Car("B", 7, 8, Direction.W, "FFLFFFFFFF");
        List<Car> cars = new ArrayList<>();
        cars.add(carA);
        cars.add(carB);

        CollisionResult result = simulationService.run(field, cars);

        // Collision is expected
        assertNotNull(result);
        assertEquals(7, result.getStep());
        assertEquals("5,4", result.getPosition());
        assertTrue(result.getCollidedCarNames().contains("A"));
        assertTrue(result.getCollidedCarNames().contains("B"));

        // Both cars should be inactive after collision
        assertFalse(carA.isActive());
        assertFalse(carB.isActive());
    }
}

