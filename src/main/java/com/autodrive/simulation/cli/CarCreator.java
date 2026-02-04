package com.autodrive.simulation.cli;

import com.autodrive.simulation.field.Field;
import com.autodrive.simulation.model.Car;
import com.autodrive.simulation.model.Direction;

/**
 * Responsible only for creating a {@link Car} from user input.
 * Keeps parsing/validation out of the main CLI flow.
 */
public class CarCreator {

    private final java.util.Scanner scanner;

    public CarCreator(java.util.Scanner scanner) {
        this.scanner = scanner;
    }

    public Car createCar(Field field) {
        System.out.print("Enter car name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Car name cannot be empty.");
            return null;
        }

        System.out.print("Enter initial position (x y Direction): ");
        String[] parts = scanner.nextLine().trim().split("\\s+");

        try {
            if (parts.length < 3) {
                System.out.println("Invalid format! Use: x y Direction (e.g., 1 2 N)");
                return null;
            }

            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            Direction direction = Direction.valueOf(parts[2].toUpperCase());

            if (!field.isInside(x, y)) {
                System.out.println("Position (" + x + ", " + y + ") is outside field boundaries!");
                return null;
            }

            System.out.print("Enter commands (L/R/F): ");
            String commands = scanner.nextLine().trim().toUpperCase();

            if (commands.isEmpty() || !commands.matches("[LRF]+")) {
                System.out.println("Invalid commands! Only L, R, F are allowed.");
                return null;
            }

            return new Car(name, x, y, direction, commands);

        } catch (NumberFormatException e) {
            System.out.println("Invalid position! Enter integers for x and y.");
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid direction! Use N, E, S, or W.");
            return null;
        }
    }
}

