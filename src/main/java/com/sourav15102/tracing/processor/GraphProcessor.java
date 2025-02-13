package com.sourav15102.tracing.processor;

import com.sourav15102.tracing.model.MicroserviceGraph;
import com.sourav15102.tracing.service.GraphService;
import com.sourav15102.tracing.util.GraphParser;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class GraphProcessor {
    public void start(){
        String inputFilePath = null;
        String outputFilePath = null;

        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            inputFilePath = prop.getProperty("inputFilePath");
            outputFilePath = prop.getProperty("outputFilePath");

        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error loading properties file. Exiting.");
            return; // Or handle the error as needed
        }

        if (inputFilePath == null || outputFilePath == null) {
            System.err.println("Input or output file path not found in properties file. Exiting.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Read multiple graphs from the file
            List<MicroserviceGraph> graphs = GraphParser.readGraphs(inputFilePath);

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
