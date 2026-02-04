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

src/
├── main/java/com/autodrive/simulation/
│   ├── SimulationMain.java          # Main entry point
│   ├── cli/
│   │   ├── SimulationCliRunner.java # Orchestrates simulation flow
│   │   ├── CarCreator.java          # Handles user input for car creation
│   │   └── SimulationResultPrinter.java # Handles console output
│   ├── model/
│   │   ├── Car.java                 # Car entity with movement logic
│   │   ├── Direction.java           # Direction enum (N/E/S/W)
│   │   └── Field.java               # Field boundaries
│   ├── service/
│   │   ├── SimulationService.java   # Service Interface
│   │   ├── SimulationServiceImpl.java # Service Implementation
│   │   └── CollisionResult.java     # Collision result data
│   └── visualization/
│       ├── MovementPatternVisualizer.java # Visualization Interface
│       └── MovementVisualizer.java  # Visualizes car movement paths
└── test/java/com/autodrive/simulation/
    └── ...                          # Unit tests

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

## Sample Input Files

For quick testing, you can create an input file and pipe it to the application:

### Single Car (No Collision)
Create a file `input_single.txt`:
```
10 10
1
A
1 2 N
FFRFFFFRRL
2
2
```

Run: `cat input_single.txt | ./gradlew run -q --console=plain`

### Collision Scenario
Create a file `input_collision.txt`:
```
10 10
1
A
1 2 N
FFRFFFFRRL
1
B
7 8 W
FFLFFFFFFF
2
2
```

Run: `cat input_collision.txt | ./gradlew run -q --console=plain`

## Usage Example

### Starting the Application

When you run the application, you'll see:

```
Welcome to Auto Driving Car Simulation!

Please enter the width and height of the simulation field in x y format:
10 10

You have created a field of 10 x 10.
```

### Adding Cars

1. Choose option `[1] Add a car to field`
2. Enter car details:
   - **Name**: `A`
   - **Position**: `1 2 N`
   - **Commands**: `FFRFFFFRRL`

### Running Simulation

1. Choose option `[2] Run simulation`
2. The program will:
   - Display movement patterns for each car
   - Run the simulation step-by-step
   - Show results:
     - If collision: `- A, collides with B at (5,4) at step 7`
     - If no collision: `- A, (5,4) S`

### Example Session (Single Car)

```
Welcome to Auto Driving Car Simulation!

Please enter the width and height of the simulation field in x y format:
10 10

You have created a field of 10 x 10.

Please choose from the following options:
[1] Add a car to field
[2] Run simulation
1

Please enter the name of the car:
A
Please enter initial position of car A in x y Direction format:
1 2 N
Please enter the commands for car A:
FFRFFFFFRRL
✓ Car 'A' added successfully!

Please choose from the following options:
[1] Add a car to field
[2] Run simulation
2

=== Simulation Summary ===
Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL

=== Movement Patterns ===
[Visualization shown here...]

=== Running Simulation ===

After simulation, the result is:
- A, (5,4) S

Please choose from the following options:
[1] Start over
[2] Exit
2

Thank you for running the simulation. Goodbye!
```

### Example Session (Collision Scenario)

```
Welcome to Auto Driving Car Simulation!

Please enter the width and height of the simulation field in x y format:
10 10

You have created a field of 10 x 10.

Please choose from the following options:
[1] Add a car to field
[2] Run simulation
1

Please enter the name of the car:
A
Please enter initial position of car A in x y Direction format:
1 2 N
Please enter the commands for car A:
FFRFFFFFRRL
✓ Car 'A' added successfully!

Please choose from the following options:
[1] Add a car to field
[2] Run simulation
1

Please enter the name of the car:
B
Please enter initial position of car B in x y Direction format:
7 8 W
Please enter the commands for car B:
FFLFFFFFFFF
✓ Car 'B' added successfully!

Please choose from the following options:
[1] Add a car to field
[2] Run simulation
2

=== Simulation Summary ===
Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL
- B, (7,8) W, FFLFFFFFFF

=== Movement Patterns ===
[Visualization shown here...]

=== Running Simulation ===

After simulation, the result is:
- A, collides with B at (5,4) at step 7
- B, collides with A at (5,4) at step 7

Please choose from the following options:
[1] Start over
[2] Exit
2

Thank you for running the simulation. Goodbye!
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

