package com.autodrive.simulation.cli;

import com.autodrive.simulation.field.Field;
import com.autodrive.simulation.model.Car;
import com.autodrive.simulation.service.CollisionResult;
import com.autodrive.simulation.service.SimulationService;
import com.autodrive.simulation.visualization.MovementPatternVisualizer;

import java.util.*;

/**
 * Command-line interface runner for the Auto Driving Car Simulation.
 * Handles user input and coordinates the simulation flow.
 */
public class SimulationCliRunner {

    private final SimulationService simulationService;
    private final MovementPatternVisualizer movementVisualizer;
    private final Scanner scanner;
    private final CarCreator carCreator;
    private final SimulationResultPrinter resultPrinter;

    /**
     * Reads an integer from the console, re-prompting until a valid number is entered.
     */
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public SimulationCliRunner(
            SimulationService simulationService,
            MovementPatternVisualizer movementVisualizer,
            Scanner scanner
    ) {
        this.simulationService = simulationService;
        this.movementVisualizer = movementVisualizer;
        this.scanner = scanner;
        this.carCreator = new CarCreator(scanner);
        this.resultPrinter = new SimulationResultPrinter();
    }

    /**
     * Starts the simulation CLI interface.
     * This is the main entry point that handles the interactive loop.
     */
    public void start() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Auto Driving Car Simulation ===");
            int width = readInt("Enter field width (positive integer): ");
            int height = readInt("Enter field height (positive integer): ");

            if (width <= 0 || height <= 0) {
                System.out.println("Field size must be positive. Please try again.");
                continue;
            }

            Field field = new Field(width, height);
            List<Car> cars = addCars(field);

            if (!cars.isEmpty()) {
                runSimulation(field, cars);
            }

            System.out.println();
            System.out.println("[1] Start over");
            System.out.println("[2] Exit");
            System.out.print("Choose option: ");
            int choice = readInt("");

            running = (choice == 1);
        }

        System.out.println();
        System.out.println("Goodbye!");
    }

    private List<Car> addCars(Field field) {
        List<Car> cars = new ArrayList<>();
        boolean addingCars = true;

        while (addingCars) {
            System.out.println();
            System.out.println("[1] Add a car");
            System.out.println("[2] Start simulation");
            System.out.print("Choose option: ");
            int choice = readInt("");

            if (choice == 1) {
                Car car = carCreator.createCar(field);
                if (car != null) {
                    cars.add(car);
                    System.out.println("✓ Car '" + car.getName() + "' added successfully!");
                }
            } else if (choice == 2) {
                addingCars = false;
            }
        }

        return cars;
    }

    private void runSimulation(Field field, List<Car> cars) {
        // Print car list before simulation
        resultPrinter.printCarList(cars);

        // Create deep copy of cars for visualization (before simulation mutates them)
        List<Car> initialCars = new ArrayList<>();
        for (Car car : cars) {
            initialCars.add(new Car(
                    car.getName(),
                    car.getX(),
                    car.getY(),
                    car.getDirection(),
                    car.getCommands()));
        }

        // Display movement patterns visualization using initial state
        movementVisualizer.displayMovementPatterns(field, initialCars);

        System.out.println();
        System.out.println("=== Running Simulation ===");
        CollisionResult collisionResult = simulationService.run(field, cars);

        resultPrinter.printSimulationResult(cars, collisionResult);
    }
}
