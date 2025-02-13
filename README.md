# Distributed Tracing System

A Java application that processes microservice traces and calculates various metrics like latency, shortest paths, and cycle detection in a directed weighted graph.

## Prerequisites

- Java JDK 19 or higher
- Maven 3.6 or higher
- Git (optional, for cloning the repository)


## Building and Running

1. **Clone the repository** (if using Git):
   ```bash
   git clone <repository-url>
   cd distributed-tracing
   ```

2. **Build the project**:
   ```bash
   mvn clean package
   ```

3. **Run the application**:
   ```bash
   java -jar target/distributed-tracing-1.0-SNAPSHOT.jar
   ```
## Configuration

The application uses `src/main/resources/config.properties` for configuration: for storing input and output file path.

## Input Format

The input file (`src/main/resources/input.txt`) should contain directed graph data in the following format:

### Sample Input
AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
AB5, BC4, CD8, DC8, DE6, AD10, CE2, EB3, AE5

Where:
- Each entry represents a directed edge (e.g., `AB5` means edge from A to B with weight 5)
- Entries are comma-separated
- Multiple graphs can be specified, one per line

### Sample Output
Graph 1:
1. 9
2. 5
3. 13
4. 22
5. NO SUCH TRACE
6. 2
7. 3
8. 9
9. 9
10. 7

Graph 2:
1. 9
2. 10
3. 18
4. 20
5. NO SUCH TRACE
6. 2
7. 3
8. 9
9. 9
10. 7


## Running Tests

Run all tests using Maven:
```bash
mvn test
```

