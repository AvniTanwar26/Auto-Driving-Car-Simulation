package com.autodrive.simulation.integration;

import com.autodrive.simulation.cli.SimulationCliRunner;
import com.autodrive.simulation.service.SimulationService;
import com.autodrive.simulation.service.SimulationServiceImpl;
import com.autodrive.simulation.visualization.MovementPatternVisualizer;
import com.autodrive.simulation.visualization.MovementVisualizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-End Integration Tests for Auto Driving Car Simulation.
 *
 * These tests verify the complete system flow from user input to final output,
 * testing both normal simulation scenarios and collision detection scenarios.
 */
class EndToEndIntegrationTest {

    /**
     * Runs simulation with fake user input and captures console output.
     *
     */
    private String runSimulation(String input) {
      
        ByteArrayInputStream inputReader = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputReader);

       
        ByteArrayOutputStream outputWriter = new ByteArrayOutputStream();
        PrintStream originalConsole = System.out;

        try {
           
            System.setOut(new PrintStream(outputWriter));

            // Run the actual simulation program
            SimulationService simulationService = new SimulationServiceImpl();
            MovementPatternVisualizer visualizer = new MovementVisualizer();
            SimulationCliRunner runner = new SimulationCliRunner(simulationService, visualizer, scanner);

            runner.start();

            return outputWriter.toString();
        } finally {
            System.setOut(originalConsole);
        }
    }

    @Test
    @DisplayName("Scenario 1: Single car simulation without collision - verifies final position")
    void testSingleCarSimulationNoCollision() {
        // Given: A 10x10 field with one car starting at (1,2) facing North
        // When: Car executes commands FFRFFFFRRL
        // Then: Car should end at position (5,4) facing South

        String input = String.join("\n",
                "10 10",      // Field size
                "1",          // Add a car
                "A",          // Car name
                "1 2 N",      // Initial position (1,2) facing North
                "FFRFFFFRRL", // Commands
                "2",          // Run simulation
                "2"           // Exit
        );

        String output = runSimulation(input);

        assertTrue(output.contains("Welcome to Auto Driving Car Simulation!"),
                "Should display welcome message");

    
        assertTrue(output.contains("You have created a field of 10 x 10"),
                "Should confirm field creation");

      
        assertTrue(output.contains("Car 'A' added successfully!"),
                "Should confirm car was added");

    
        assertTrue(output.contains("Your current list of cars are:"),
                "Should display car list header");

        assertTrue(output.contains("- A, (1,2) N, FFRFFFFRRL"),
                "Should show car A with initial position and commands");

     
        assertTrue(output.contains("After simulation, the result is:"),
                "Should display simulation results header");

        assertTrue(output.contains("- A, (5,4) S"),
                "Car A should end at position (5,4) facing South");

     
        assertFalse(output.contains("collides"),
                "Should not contain collision message for single car");

    
        assertTrue(output.contains("Thank you for running the simulation. Goodbye!"),
                "Should display goodbye message");
    }

    @Test
    @DisplayName("Scenario 2: Multiple cars simulation with collision detection")
    void testMultipleCarSimulationWithCollision() {
        // Given: A 10x10 field with two cars
        //   - Car A starting at (1,2) facing North with commands FFRFFFFRRL
        //   - Car B starting at (7,8) facing West with commands FFLFFFFFFF
        // When: Both cars execute their commands simultaneously
        // Then: Cars should collide at position (5,4) at step 7

        String input = String.join("\n",
                "10 10",      // Field size
                "1",          // Add first car
                "A",          // Car A name
                "1 2 N",      // Car A initial position
                "FFRFFFFRRL", // Car A commands
                "1",          // Add second car
                "B",          // Car B name
                "7 8 W",      // Car B initial position
                "FFLFFFFFFF", // Car B commands
                "2",          // Run simulation
                "2"           // Exit
        );

        String output = runSimulation(input);

        assertTrue(output.contains("Welcome to Auto Driving Car Simulation!"),
                "Should display welcome message");

 
        assertTrue(output.contains("You have created a field of 10 x 10"),
                "Should confirm field creation");

        assertTrue(output.contains("Car 'A' added successfully!"),
                "Should confirm car A was added");
        assertTrue(output.contains("Car 'B' added successfully!"),
                "Should confirm car B was added");

       
        assertTrue(output.contains("Your current list of cars are:"),
                "Should display car list header");
        assertTrue(output.contains("- A, (1,2) N, FFRFFFFRRL"),
                "Should show car A with initial position and commands");
        assertTrue(output.contains("- B, (7,8) W, FFLFFFFFFF"),
                "Should show car B with initial position and commands");

       
        assertTrue(output.contains("After simulation, the result is:"),
                "Should display simulation results header");

       
        assertTrue(output.contains("- A, collides with B at (5,4) at step 7"),
                "Car A should collide with B at (5,4) at step 7");
        assertTrue(output.contains("- B, collides with A at (5,4) at step 7"),
                "Car B should collide with A at (5,4) at step 7");

    
        assertTrue(output.contains("Thank you for running the simulation. Goodbye!"),
                "Should display goodbye message");
    }

    @Test
    @DisplayName("Scenario 3: Multiple cars simulation without collision")
    void testMultipleCarSimulationNoCollision() {
        // Given: A 10x10 field with two cars that won't collide
        // When: Both cars execute their commands
        // Then: Both cars should reach their final positions without collision

        String input = String.join("\n",
                "10 10",    // Field size
                "1",        // Add first car
                "X",        // Car X name
                "0 0 E",    // Car X starts at bottom-left facing East
                "FFF",      // Move 3 steps east
                "1",        // Add second car
                "Y",        // Car Y name
                "9 9 W",    // Car Y starts at top-right facing West
                "FFF",      // Move 3 steps west
                "2",        // Run simulation
                "2"         // Exit
        );

        String output = runSimulation(input);


        assertTrue(output.contains("Car 'X' added successfully!"),
                "Should confirm car X was added");
        assertTrue(output.contains("Car 'Y' added successfully!"),
                "Should confirm car Y was added");

  
        assertTrue(output.contains("After simulation, the result is:"),
                "Should display simulation results");
        assertTrue(output.contains("- X, (3,0) E"),
                "Car X should end at (3,0) facing East");
        assertTrue(output.contains("- Y, (6,9) W"),
                "Car Y should end at (6,9) facing West");

     
        assertFalse(output.contains("collides"),
                "Should not contain collision message");
    }

    @Test
    @DisplayName("Scenario 4: Car respects field boundaries")
    void testCarRespectsBoundaries() {
        // Given: A 5x5 field with a car at the edge
        // When: Car tries to move beyond the boundary
        // Then: Car should stay at the boundary

        String input = String.join("\n",
                "5 5",      // Small field
                "1",        // Add a car
                "C",        // Car name
                "0 0 S",    // Start at bottom-left facing South
                "FFF",      // Try to move south 3 times (all should be ignored)
                "2",        // Run simulation
                "2"         // Exit
        );

        String output = runSimulation(input);

        // Verify car stays at (0,0) as moves beyond boundary are ignored
        assertTrue(output.contains("- C, (0,0) S"),
                "Car C should remain at (0,0) as boundary moves are ignored");
    }

    @Test
    @DisplayName("Scenario 5: Car rotation commands work correctly")
    void testCarRotationCommands() {
        // Given: A car at position (2,2)
        // When: Car executes only rotation commands
        // Then: Car should remain at same position but change direction

        String input = String.join("\n",
                "10 10",    // Field size
                "1",        // Add a car
                "R",        // Car name
                "2 2 N",    // Start at (2,2) facing North
                "RRRR",     // Rotate right 4 times (full circle)
                "2",        // Run simulation
                "2"         // Exit
        );

        String output = runSimulation(input);

      
        assertTrue(output.contains("- R, (2,2) N"),
                "Car R should remain at (2,2) facing North after full rotation");
    }

    @Test
    @DisplayName("Scenario 6: Complex path with mixed commands")
    void testComplexPathWithMixedCommands() {
        // Given: A car with a complex set of commands
        // When: Car executes forward, left, and right commands
        // Then: Car should follow the expected path

        String input = String.join("\n",
                "15 15",    // Larger field
                "1",        // Add a car
                "M",        // Car name
                "5 5 N",    // Start at (5,5) facing North
                "FFLFFRF",  // Complex path: FF (north to 5,7), L (face west),
                            // FF (west to 3,7), R (face north), F (north to 3,8)
                "2",        // Run simulation
                "2"         // Exit
        );

        String output = runSimulation(input);

      
        assertTrue(output.contains("- M, (3,8) N"),
                "Car M should end at (3,8) facing North after complex path");
    }
}
