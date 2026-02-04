package com.autodrive.simulation.cli;

import com.autodrive.simulation.model.Car;
import com.autodrive.simulation.service.CollisionResult;

import java.util.List;

/**
 * Responsible only for printing results to the console.
 */
public class SimulationResultPrinter {

    public SimulationResultPrinter() {
    }

    public void printCarList(List<Car> cars) {
        System.out.println("Your current list of cars are:");
        for (Car car : cars) {
            System.out.println(String.format("- %s, (%d,%d) %s, %s",
                    car.getName(),
                    car.getX(),
                    car.getY(),
                    car.getDirection(),
                    car.getCommands()));
        }
    }

    public void printSimulationResult(List<Car> cars, CollisionResult collisionResult) {
        System.out.println();
        System.out.println("After simulation, the result is:");

        if (collisionResult != null) {
            for (Car car : cars) {
                if (!car.isActive()) {
                    System.out.println(String.format("- %s, collides at (%s) at step %d",
                            car.getName(),
                            collisionResult.getPosition(),
                            collisionResult.getStep()));
                }
            }
            return;
        }

        for (Car car : cars) {
            System.out.println(String.format("- %s, (%d,%d) %s",
                    car.getName(),
                    car.getX(),
                    car.getY(),
                    car.getDirection()));
        }
    }
}

