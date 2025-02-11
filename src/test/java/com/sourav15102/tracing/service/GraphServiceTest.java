package com.sourav15102.tracing.service;

import com.sourav15102.tracing.model.MicroserviceGraph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class GraphServiceTest {

    @Test
    public void testTraceLatency_failsInitially() {
        MicroserviceGraph graph = new MicroserviceGraph();
        GraphService service = new GraphService(graph);

        assertEquals(9, service.getTraceLatency(Arrays.asList("A", "B", "C"))); // Should fail initially
    }

    @Test
    public void testCountPathsWithConstraints_failsInitially() {
        MicroserviceGraph graph = new MicroserviceGraph();
        GraphService service = new GraphService(graph);

        assertEquals(2, service.countPathsWithConstraints("C", "C", 3, false)); // Should fail initially
    }

    @Test
    public void testFindShortestPath_failsInitially() {
        MicroserviceGraph graph = new MicroserviceGraph();
        GraphService service = new GraphService(graph);

        assertEquals(9, service.findShortestPath("A", "C")); // Should fail initially
    }

    @Test
    public void testCountCyclesWithMaxLatency_failsInitially() {
        MicroserviceGraph graph = new MicroserviceGraph();
        GraphService service = new GraphService(graph);

        assertEquals(7, service.countCyclesWithMaxLatency("C", 30)); // Should fail initially
    }
}
