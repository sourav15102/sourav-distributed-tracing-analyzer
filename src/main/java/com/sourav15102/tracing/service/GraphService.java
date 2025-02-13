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

    private int dijAlgo(String start, String end) {
        if (!graph.containsNode(start) || !graph.containsNode(end)) return -1;

        // Min-Heap to store nodes with their shortest distance found so far
        PriorityQueue<Connection> minHeap = new PriorityQueue<>(Comparator.comparingInt(Connection::getLatency));
        Map<String, Integer> shortestDistances = new HashMap<>();
        Set<String> visited = new HashSet<>();

        // Initialize distances
        shortestDistances.put(start, 0);
        minHeap.add(new Connection(start, 0));

        while (!minHeap.isEmpty()) {
            Connection current = minHeap.poll();
            String currentNode = current.getDestination();

            // If we reach the target node, return the shortest distance
            if (currentNode.equals(end)) return shortestDistances.get(currentNode);

            // If the node was already processed, skip it
            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            // Explore neighbors
            for (Connection neighbor : graph.getEdges(currentNode)) {
                if (visited.contains(neighbor.getDestination())) continue;

                int newDistance = shortestDistances.get(currentNode) + neighbor.getLatency();
                if (newDistance < shortestDistances.getOrDefault(neighbor.getDestination(), Integer.MAX_VALUE)) {
                    shortestDistances.put(neighbor.getDestination(), newDistance);
                    minHeap.add(new Connection(neighbor.getDestination(), newDistance));
                }
            }
        }

        return -1; // No path found
    }

    private int dfsCountCycles(String start, String end, int maxLatency, int currentLatency) {
        // Stop exploring this path if the latency exceeds the max limit
        int count = 0;
        if (currentLatency >= maxLatency) return 0;
        if(start.equals(end)){
            count = 1;
        }

        for (Connection connection : graph.getEdges(start)) {
            int newLatency = currentLatency + connection.getLatency();

            // Continue DFS without marking nodes as visited
            count += dfsCountCycles(connection.getDestination(), end, maxLatency, newLatency);
        }

        return count;
    }
    // Utility method for tracing requests and calculate average latency
    public int getAverageLatency(List<String> path) {
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

    // Implementing Q1-Q5: Compute total latency for a given trace
    public String getTraceLatency(List<String> path) {
        int totalLatency = getAverageLatency(path);
        return totalLatency==-1?"NO SUCH TRACE":Integer.toString(totalLatency);
    }

    // Q6: Count paths with at most maxHops
    public int countPathsWithMaxHops(String start, String end, int maxHops) {
        return dfsMaxHops(start, end, maxHops, 0);
    }

    // Q7: Count paths with exactly exactHops
    public int countPathsWithExactHops(String start, String end, int exactHops) {
        return dfsExactHops(start, end, exactHops, 0);
    }

    // Q8-Q9: Find the shortest path using Dijkstra’s Algorithm
    public int findShortestPath(String start, String end) {
        int shortestPath = Integer.MAX_VALUE;
        boolean found = false;
        int temp;
        for (Connection neighbor : graph.getEdges(start)) {
            temp = dijAlgo(neighbor.getDestination(), end);
            if(temp!=-1){
                found = true;
                shortestPath = Math.min(shortestPath, temp+neighbor.getLatency());
            }
        }
        return found?shortestPath:-1;
    }

    // Q10: Count different cycles that start and end at `node` within `maxLatency`
    public int countCyclesWithMaxLatency(String start, String end, int maxLatency) {
        int count = 0;
        for (Connection connection : graph.getEdges(start)) {
            count += dfsCountCycles(connection.getDestination(), end, maxLatency, connection.getLatency());
        }
        return count;
    }
}

