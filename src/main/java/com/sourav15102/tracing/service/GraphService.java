package com.sourav15102.tracing.service;
import com.sourav15102.tracing.model.Connection;
import com.sourav15102.tracing.model.MicroserviceGraph;

import java.util.*;

public class GraphService {
    private final MicroserviceGraph graph;

    public GraphService(MicroserviceGraph graph) {
        this.graph = graph;
    }

    // Implementing Q1-Q4: Compute total latency for a given trace
    public int getTraceLatency(List<String> path) {
        int totalLatency = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            String current = path.get(i);
            String next = path.get(i + 1);
            boolean found = false;

            for (Connection connection : graph.getEdges(current)) {
                if (connection.getDestination().equals(next)) {
                    totalLatency += connection.getLatency();
                    found = true;
                     break;
                }
            }

            if (!found) return -1; // If any step in the path is missing, return NO SUCH TRACE (-1)
        }

        return totalLatency;
    }

    // Count Paths with Constraints (Q6-Q7)
    public int countPathsWithConstraints(String start, String end, int maxHops, boolean exactHops) {
        return 0; // Placeholder
    }

    // Find the Shortest Path (Q8-Q9)
    public int findShortestPath(String start, String end) {
        return -1; // Placeholder
    }

    // Count Different Cycles Within a Max Latency (Q10)
    public int countCyclesWithMaxLatency(String node, int maxLatency) {
        return 0; // Placeholder
    }
}

