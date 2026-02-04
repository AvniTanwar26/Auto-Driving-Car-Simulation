package com.autodrive.simulation.visualization;

import com.autodrive.simulation.field.Field;
import com.autodrive.simulation.model.Car;

import java.util.List;

/**
 * Abstraction for visualizing movement patterns.
 * Allows the CLI to depend on an interface rather than a concrete class.
 */
public interface MovementPatternVisualizer {
    void displayMovementPatterns(Field field, List<Car> cars);
}

