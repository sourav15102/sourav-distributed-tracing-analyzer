package com.sourav15102.tracing.service;
import com.sourav15102.tracing.model.Connection;
import com.sourav15102.tracing.model.MicroserviceGraph;

import java.util.*;

public class GraphService {
    private final MicroserviceGraph graph;

    public GraphService(MicroserviceGraph graph) {
        this.graph = graph;
    }

    private int dfsMaxHops(String current, String end, int maxHops, int hops) {
        if (hops > maxHops) return 0;
        if (current.equals(end) && hops > 0) return 1; // Found a valid path

        int count = 0;
        for (Connection connection : graph.getEdges(current)) {
            count += dfsMaxHops(connection.getDestination(), end, maxHops, hops + 1);
        }
        return count;
    }

    private int dfsExactHops(String current, String end, int exactHops, int hops) {
        if (hops > exactHops) return 0;
        if (hops == exactHops) return current.equals(end) ? 1 : 0;

        int count = 0;
        for (Connection connection : graph.getEdges(current)) {
            count += dfsExactHops(connection.getDestination(), end, exactHops, hops + 1);
        }
        return count;
    }

    // Implementing Q1-Q5: Compute total latency for a given trace
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

    // Q6: Count paths with at most maxHops
    public int countPathsWithMaxHops(String start, String end, int maxHops) {
        return dfsMaxHops(start, end, maxHops, 0);
    }

    // Q7: Count paths with exactly exactHops
    public int countPathsWithExactHops(String start, String end, int exactHops) {
        return dfsExactHops(start, end, exactHops, 0);
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

