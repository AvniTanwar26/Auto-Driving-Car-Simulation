# Auto Driving Car Simulation

A simple Java console application that simulates autonomous cars moving on a rectangular field with collision detection.

## Problem Statement

This program simulates one or more autonomous cars on a rectangular field. Each car can:
- Start at any position with a facing direction (North, East, South, West)
- Execute commands: `L` (turn left), `R` (turn right), `F` (move forward)
- Respect field boundaries (moves outside the field are ignored)
- Detect collisions when multiple cars occupy the same position at the same step

## Prerequisites

- **Java 17** or higher
- **Gradle 7.0+** (or use the included Gradle wrapper)

To check your Java version:
```bash
java -version
```

## Project Structure

```
src/
├── main/java/com/autodrive/simulation/
│   ├── SimulationMain.java          # Main entry point
│   ├── cli/
│   │   ├── SimulationCliRunner.java # CLI interface handler
│   │   └── MovementVisualizer.java  # Visualizes car movement paths
│   ├── model/
│   │   ├── Car.java                 # Car entity with movement logic
│   │   ├── Direction.java          # Direction enum (N/E/S/W)
│   │   └── Field.java              # Field boundaries
│   └── service/
│       ├── SimulationService.java  # Core simulation engine
│       └── CollisionResult.java    # Collision result data
└── test/java/com/autodrive/simulation/
    └── ...                         # Unit tests
```

## How to Run

### Option 1: Using Gradle Wrapper (Recommended)

**On macOS/Linux:**
```bash
./gradlew run
```

**On Windows:**
```bash
gradlew.bat run
```

### Option 2: Build and Run JAR

1. Build the application:
```bash
./gradlew build
```

2. Run the JAR:
```bash
java -jar build/libs/Auto-driving-car-simulation-1.0.0.jar
```

### Option 3: Using IDE

1. Open the project in your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Navigate to `src/main/java/com/autodrive/simulation/SimulationMain.java`
3. Run the `main` method

## Usage Example

### Starting the Application

When you run the application, you'll see:

```
=== Auto Driving Car Simulation ===
Enter field width (positive integer): 10
Enter field height (positive integer): 10
```

### Adding Cars

1. Choose option `[1] Add a car`
2. Enter car details:
   - **Name**: e.g., `A`
   - **Position**: `x y Direction` (e.g., `1 2 N`)
   - **Commands**: String of `L`, `R`, `F` (e.g., `FFRFFFFRRL`)

### Running Simulation

1. Choose option `[2] Start simulation`
2. The program will:
   - Display movement patterns for each car
   - Run the simulation step-by-step
   - Show results:
     - If collision: `Car A, collides at (5,4) at step 7`
     - If no collision: `Car A, (5,4) S` (final position and direction)

### Example Session

```
=== Auto Driving Car Simulation ===
Enter field width (positive integer): 10
Enter field height (positive integer): 10

[1] Add a car
[2] Start simulation
Choose option: 1

Enter car name: A
Enter initial position (x y Direction): 1 2 N
Enter commands (L/R/F): FFRFFFFRRL
✓ Car 'A' added successfully!

[1] Add a car
[2] Start simulation
Choose option: 2

Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL

=== Movement Patterns ===
[Visualization shown here...]

=== Running Simulation ===

After simulation, the result is:
- A, (5,4) S
```

## Running Tests

To run all unit tests:

```bash
./gradlew test
```

To generate test coverage report:

```bash
./gradlew jacocoTestReport
```

Coverage report will be available at: `build/reports/jacoco/test/html/index.html`

## Building the Project

```bash
./gradlew build
```

This will:
- Compile the source code
- Run all tests
- Generate a JAR file in `build/libs/`

## Key Features

- ✅ **Simple Java Application**: No framework dependencies, pure Java
- ✅ **Command-line Interface**: Interactive console-based UI
- ✅ **Collision Detection**: Detects when cars collide at the same position
- ✅ **Boundary Validation**: Prevents cars from moving outside the field
- ✅ **Movement Visualization**: Visual grid showing car paths
- ✅ **Unit Tests**: Comprehensive test coverage with JUnit 5
- ✅ **Code Coverage**: JaCoCo integration for coverage reports

## Design Principles

This project follows:
- **SOLID Principles**: Single Responsibility, Open/Closed, etc.
- **OOP Best Practices**: Encapsulation, proper abstraction
- **Clean Code**: Readable, maintainable, well-documented

## License

This is a demonstration project for educational purposes.
