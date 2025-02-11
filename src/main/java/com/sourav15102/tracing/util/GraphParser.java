package com.sourav15102.tracing.util;
import com.sourav15102.tracing.model.MicroserviceGraph;

import java.io.*;

public class GraphParser {
    public static MicroserviceGraph parseGraph(String filePath) throws IOException {
        MicroserviceGraph graph = new MicroserviceGraph();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line = reader.readLine();
        while (line != null) {
            String[] parts = line.split(",\\s*");
            for (String part : parts) {
                String from = part.substring(0, 1);
                String to = part.substring(1, 2);
                int latency = Integer.parseInt(part.substring(2));
                graph.addEdge(from, to, latency);
            }
            line = reader.readLine();
        }

        reader.close();
        return graph;
    }
}
