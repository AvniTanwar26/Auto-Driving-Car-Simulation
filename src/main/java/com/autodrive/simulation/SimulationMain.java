package com.autodrive.simulation;

import com.autodrive.simulation.cli.SimulationCliRunner;
import com.autodrive.simulation.visualization.MovementPatternVisualizer;
import com.autodrive.simulation.visualization.MovementVisualizer;
import com.autodrive.simulation.service.SimulationService;
import com.autodrive.simulation.service.SimulationServiceImpl;

import java.util.Scanner;

/**
 * Main entry point for the Auto Driving Car Simulation application.
 * This is a simple Java console application that simulates autonomous cars
 * moving on a rectangular field with collision detection.
 */
public class SimulationMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimulationService simulationService = new SimulationServiceImpl();
        MovementPatternVisualizer visualizer = new MovementVisualizer();

        SimulationCliRunner runner = new SimulationCliRunner(simulationService, visualizer, scanner);
        runner.start();
    }
}
