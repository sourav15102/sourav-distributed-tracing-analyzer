package com.sourav15102.tracing.util;

import com.sourav15102.tracing.model.MicroserviceGraph;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GraphParser {
    public static List<MicroserviceGraph> readGraphs(String filePath) throws IOException {
        List<MicroserviceGraph> graphs = new ArrayList<>();

        // Read all lines from the input file (each line is a separate graph)
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for (String line : lines) {
            MicroserviceGraph graph = new MicroserviceGraph();
            String[] edges = line.split(",\\s*");

            for (String edge : edges) {
                String from = edge.substring(0, 1);  // First character is the 'from' node
                String to = edge.substring(1, 2);    // Second character is the 'to' node
                int latency = Integer.parseInt(edge.substring(2)); // Remaining characters represent latency
                graph.addEdge(from, to, latency);
            }

            graphs.add(graph);
        }

        return graphs;
    }
}
