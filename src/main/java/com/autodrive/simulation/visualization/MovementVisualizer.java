package com.autodrive.simulation.visualization;

import com.autodrive.simulation.model.Field;
import com.autodrive.simulation.model.Car;
import com.autodrive.simulation.model.Direction;

import java.util.*;

public class MovementVisualizer implements MovementPatternVisualizer {

    /**
     * Displays the movement pattern for each car on a 2D grid
     */
    @Override
    public void displayMovementPatterns(Field field, List<Car> cars) {
        System.out.println("\n=== Movement Patterns ===\n");

        for (Car car : cars) {
            displayCarMovementPattern(field, car);
        }
    }

    /**
     * Displays the movement pattern for a single car
     */
    private void displayCarMovementPattern(Field field, Car car) {
        System.out.println("Car: " + car.getName() +
                " (Starting at " + car.getX() + ", " + car.getY() +
                " facing " + car.getDirection() + ")");

        // Create a empty grid to track positions
        char[][] grid = createEmptyGrid(field);

        // Track the movement path
        List<CarPosition> path = calculatePath(field, car);

        // Mark positions on grid
        markPathOnGrid(grid, path);

        // Display grid
        printGrid(grid, field);

        // Display movement legend
        printMovementLegend(path);

        System.out.println();
    }

    /**
     * Calculate the path of movement for a car
     */
    private List<CarPosition> calculatePath(Field field, Car car) {
        List<CarPosition> path = new ArrayList<>();
        int currentX = car.getX();
        int currentY = car.getY();
        Direction currentDirection = car.getDirection();

        // Add starting position
        path.add(new CarPosition(currentX, currentY, currentDirection, 0, "START"));

        // Simulate the car's movement
        String commands = car.getCommands();
        for (int step = 0; step < commands.length(); step++) {
            char command = commands.charAt(step);

            switch (command) {
                case 'L':
                    currentDirection = currentDirection.left();
                    path.add(new CarPosition(currentX, currentY, currentDirection, step + 1, "LEFT"));
                    break;
                case 'R':
                    currentDirection = currentDirection.right();
                    path.add(new CarPosition(currentX, currentY, currentDirection, step + 1, "RIGHT"));
                    break;
                case 'F':
                    int nextX = currentX + currentDirection.dx;
                    int nextY = currentY + currentDirection.dy;

                    if (field.isInside(nextX, nextY)) {
                        currentX = nextX;
                        currentY = nextY;
                        path.add(new CarPosition(currentX, currentY, currentDirection, step + 1, "FORWARD"));
                    } else {
                        // Move ignored, stay in place
                        path.add(new CarPosition(currentX, currentY, currentDirection, step + 1, "FORWARD (ignored, boundary)"));
                    }
                    break;
                default:
                    // Invalid command - skip
                    break;
            }
        }

        return path;
    }

    /**
     * Create an empty grid representation
     */
    private char[][] createEmptyGrid(Field field) {
        char[][] grid = new char[field.getHeight()][field.getWidth()];
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                grid[y][x] = '.';
            }
        }
        return grid;
    }

    /**
     * Mark the path on the grid
     */
    private void markPathOnGrid(char[][] grid, List<CarPosition> path) {
        for (int i = 0; i < path.size(); i++) {
            CarPosition pos = path.get(i);
            char symbol;

            if (i == 0) {
                symbol = 'S'; // Start
            } else if (i == path.size() - 1) {
                symbol = 'E'; // End
            } else {
                symbol = getDirectionSymbol(pos.direction);
            }

            int gridY = grid.length - 1 - pos.y; // Flip Y for display
            grid[gridY][pos.x] = symbol;
        }
    }

    /**
     * Get direction symbol for grid display
     */
    private char getDirectionSymbol(Direction direction) {
        return switch (direction) {
            case N -> '↑';
            case E -> '→';
            case S -> '↓';
            case W -> '←';
        };
    }

    /**
     * Print the grid with coordinates
     */
    private void printGrid(char[][] grid, Field field) {
        // Print top border with X coordinates
        System.out.print("    x→ ");
        for (int x = 0; x < field.getWidth(); x++) {
            System.out.print(x + " ");
        }
        System.out.println();

        // Print grid with Y coordinates
        for (int y = 0; y < grid.length; y++) {
            System.out.printf("y=%d | ", grid.length - 1 - y);
            for (int x = 0; x < grid[y].length; x++) {
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }

        // Print bottom border
        System.out.print("    +");
        for (int x = 0; x < field.getWidth(); x++) {
            System.out.print("-+");
        }
        System.out.println();
    }

    /**
     * Print movement legend/summary
     */
    private void printMovementLegend(List<CarPosition> path) {
        System.out.println("Movement Summary:");
        System.out.println("  S = Start position");
        System.out.println("  E = End position");
        System.out.println("  ↑↓←→ = Direction faced");
        System.out.println();
        System.out.println("Step-by-step execution:");

        for (CarPosition pos : path) {
            System.out.printf("  Step %d: %s at (%d, %d) facing %s%n",
                    pos.step, pos.action, pos.x, pos.y, pos.direction);
        }
    }

    /**
     * Inner class to represent a position in the path
     */
    private static class CarPosition {
        int x;
        int y;
        Direction direction;
        int step;
        String action;

        CarPosition(int x, int y, Direction direction, int step, String action) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.step = step;
            this.action = action;
        }
    }
}
