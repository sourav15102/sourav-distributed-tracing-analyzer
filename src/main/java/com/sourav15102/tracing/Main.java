package com.sourav15102.tracing;
import com.sourav15102.tracing.model.MicroserviceGraph;
import com.sourav15102.tracing.service.GraphService;
import com.sourav15102.tracing.util.GraphFileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "src/main/resources/input.txt"; // Input file location
        String outputFilePath = "src/main/resources/output.txt"; // Output file location

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Read multiple graphs from the file
            List<MicroserviceGraph> graphs = GraphFileReader.readGraphs(inputFilePath);

            int graphIndex = 1;
            for (MicroserviceGraph graph : graphs) {
                GraphService service = new GraphService(graph);

                // Write header for each graph
                writer.write("Graph " + graphIndex + ":\n");

                // Run Algorithms and Write Output
                writer.write("1. " + service.getTraceLatency(List.of("A", "B", "C")) + "\n");
                writer.write("2. " + service.getTraceLatency(List.of("A", "D")) + "\n");
                writer.write("3. " + service.getTraceLatency(List.of("A", "D", "C")) + "\n");
                writer.write("4. " + service.getTraceLatency(List.of("A", "E", "B", "C", "D")) + "\n");
                writer.write("5. " + service.getTraceLatency(List.of("A", "E", "D")) + "\n");

                writer.write("6. " + service.countPathsWithMaxHops("C", "C", 3) + "\n");
                writer.write("7. " + service.countPathsWithExactHops("A", "C", 4) + "\n");

                writer.write("8. " + service.findShortestPath("A", "C") + "\n");
                writer.write("9. " + service.findShortestPath("B", "B") + "\n");

                writer.write("10. " + service.countCyclesWithMaxLatency("C", "C", 30) + "\n");

                // Add a newline for better readability
                writer.write("\n");

                // Increment graph count
                graphIndex++;
            }

            System.out.println("Results written to " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}