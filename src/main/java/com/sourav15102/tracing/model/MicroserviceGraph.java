package com.sourav15102.tracing.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MicroserviceGraph {
    private final Map<String, List<Connection>> graph = new HashMap<>();

    public void addEdge(String from, String to, int latency) {
        this.graph.computeIfAbsent(from, k -> new ArrayList<>()).add(new Connection(to, latency));
    }

    public List<Connection> getEdges(String service) {
        return this.graph.getOrDefault(service, new ArrayList<>());
    }

    public boolean containsNode(String service) {
        return this.graph.containsKey(service);
    }
}
