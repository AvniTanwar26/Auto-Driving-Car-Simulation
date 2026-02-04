package com.autodrive.simulation.service;

import com.autodrive.simulation.model.Field;
import com.autodrive.simulation.model.Car;

import java.util.*;

public class SimulationServiceImpl implements SimulationService {

    /**
     * Runs the simulation for the given field and list of cars.
     * Returns {@link CollisionResult} when a collision occurs (and marks collided cars inactive).
     * Returns {@code null} if no collision occurred.
     */
    @Override
    public CollisionResult run(Field field, List<Car> cars) {
        if (cars == null || cars.isEmpty()) {
            return null;
        }

        int maxSteps = cars.stream()
                .mapToInt(c -> c.getCommands() == null ? 0 : c.getCommands().length())
                .max()
                .orElse(0);

        for (int step = 0; step < maxSteps; step++) {

            for (Car car : cars) {
                String commands = car.getCommands();
                if (car.isActive() && step < (commands == null ? 0 : commands.length())) {
                    car.execute(commands.charAt(step), field);
                }
            }

            Map<String, List<Car>> positionMap = new HashMap<>();
            for (Car car : cars) {
                if (car.isActive()) {
                    positionMap
                            .computeIfAbsent(car.positionKey(), k -> new ArrayList<>())
                            .add(car);
                }
            }

            for (Map.Entry<String, List<Car>> entry : positionMap.entrySet()) {
                if (entry.getValue().size() > 1) {
                    int collisionStep = step + 1;
                    String pos = entry.getKey();

                    List<String> names = new ArrayList<>();
                    for (Car car : entry.getValue()) {
                        car.deactivate();
                        names.add(car.getName());
                    }

                    return new CollisionResult(collisionStep, pos, names);
                }
            }
        }

        return null;
    }
}
