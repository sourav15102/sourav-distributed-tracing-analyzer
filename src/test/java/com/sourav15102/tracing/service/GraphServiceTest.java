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
}
