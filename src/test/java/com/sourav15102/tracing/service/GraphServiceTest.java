package com.sourav15102.tracing.service;

import com.sourav15102.tracing.model.MicroserviceGraph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class GraphServiceTest {

    @Test
    public void testGetTraceLatency_validPath() {
        MicroserviceGraph graph = new MicroserviceGraph();
        graph.addEdge("A", "B", 5);
        graph.addEdge("B", "C", 4);
        graph.addEdge("A", "D", 5);
        graph.addEdge("D", "C", 8);

        GraphService service = new GraphService(graph);

        assertEquals(9, service.getTraceLatency(Arrays.asList("A", "B", "C"))); // Expected: 5 + 4 = 9
        assertEquals(5, service.getTraceLatency(Arrays.asList("A", "D")));     // Expected: 5
        assertEquals(13, service.getTraceLatency(Arrays.asList("A", "D", "C"))); // Expected: 5 + 8 = 13
        assertEquals(-1, service.getTraceLatency(Arrays.asList("A", "E", "D"))); // No path exists
    }

    @Test
    public void testGetTraceLatency_invalidPath() {
        MicroserviceGraph graph = new MicroserviceGraph();
        graph.addEdge("A", "B", 5);
        graph.addEdge("B", "C", 4);

        GraphService service = new GraphService(graph);

        assertEquals(-1, service.getTraceLatency(Arrays.asList("A", "C"))); // No direct path from A to C
        assertEquals(-1, service.getTraceLatency(Arrays.asList("C", "A"))); // C -> A does not exist
    }

    @Test
    public void testCountPathsWithMaxHops() {
        MicroserviceGraph graph = new MicroserviceGraph();
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "D", 5);
        graph.addEdge("A", "E", 7);
        graph.addEdge("C", "D", 8);
        graph.addEdge("D", "C", 8);
        graph.addEdge("D", "E", 6);
        graph.addEdge("C", "E", 2);
        graph.addEdge("E", "B", 3);
        graph.addEdge("B", "C", 4);

        GraphService service = new GraphService(graph);

        assertEquals(2, service.countPathsWithMaxHops("C", "C", 3)); // Expected: C-D-C and C-E-B-C
        assertEquals(0, service.countPathsWithMaxHops("A", "C", 3)); // No paths from A to C
    }

    @Test
    public void testCountPathsWithExactHops() {
        MicroserviceGraph graph = new MicroserviceGraph();
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "D", 5);
        graph.addEdge("A", "E", 7);
        graph.addEdge("C", "D", 8);
        graph.addEdge("D", "C", 8);
        graph.addEdge("D", "E", 6);
        graph.addEdge("C", "E", 2);
        graph.addEdge("E", "B", 3);
        graph.addEdge("B", "C", 4);

        GraphService service = new GraphService(graph);

        assertEquals(3, service.countPathsWithExactHops("A", "C", 4)); // Expected: A->B->C->D->C, A->D->C->D->C, A->D->E->B->C
        assertEquals(1, service.countPathsWithExactHops("A", "E", 2)); // No direct two-hop path
    }

    @Test
    public void testFindShortestPath_validPaths() {
        MicroserviceGraph graph = new MicroserviceGraph();
        graph.addEdge("A", "B", 5);
        graph.addEdge("B", "C", 4);
        graph.addEdge("A", "C", 9);
        graph.addEdge("C", "D", 8);
        graph.addEdge("D", "C", 8);
        graph.addEdge("D", "E", 6);
        graph.addEdge("A", "D", 5);
        graph.addEdge("C", "E", 2);
        graph.addEdge("E", "B", 3);
        graph.addEdge("A", "E", 7);

        GraphService service = new GraphService(graph);

        assertEquals(9, service.findShortestPath("A", "C")); // Expected: A -> B -> C (5 + 4 = 9)
        assertEquals(9, service.findShortestPath("B", "B")); // Expected: B -> C -> E -> B (4 + 2 + 3 = 9)
    }

    @Test
    public void testFindShortestPath_noPathExists() {
        MicroserviceGraph graph = new MicroserviceGraph();
        graph.addEdge("A", "B", 5);
        graph.addEdge("B", "C", 4);

        GraphService service = new GraphService(graph);

        assertEquals(-1, service.findShortestPath("C", "A")); // No reverse path from C to A
        assertEquals(-1, service.findShortestPath("D", "E")); // D and E are not connected
    }
}
