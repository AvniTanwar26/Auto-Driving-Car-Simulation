package com.autodrive.simulation.service;

import com.autodrive.simulation.field.Field;
import com.autodrive.simulation.model.Car;

import java.util.List;

/**
 * Abstraction for running the simulation.
 * This allows the CLI (or any other client) to depend on an interface
 * instead of a concrete implementation (Dependency Inversion).
 */
public interface SimulationService {

    /**
     * Runs the simulation for the given field and cars.
     *
     * @return a {@link CollisionResult} when a collision occurs; otherwise {@code null}.
     */
    CollisionResult run(Field field, List<Car> cars);
}

