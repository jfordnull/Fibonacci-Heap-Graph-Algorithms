import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private ArrayList<int[]>[] adjacencyList;
    public final int V, E;

    public Graph(int V, int E){
        this.V = V;
        this.E = E;
        adjacencyList = new ArrayList[V];
        for (int i = 0; i < V; i++){adjacencyList[i] = new ArrayList<>();}
    }

    public void addEdge(int source, int destination, int weight){
        int[] edge = {destination,weight};
        adjacencyList[source].add(edge);
        edge = new int[]{source,weight};
        adjacencyList[destination].add(edge);
    }

    public ArrayList<int[]> getAdjacentEdges(int vertex){return adjacencyList[vertex];}

    public void printGraph(){
        for (int i = 0; i < V; i++) {
            for (int[] edge : adjacencyList[i]) {
                int destination = edge[0];
                int weight = edge[1];
                System.out.println("Vertex " + i + " has an edge with " + destination +
                        " weight " + weight);
            }
        }
    }

    /*
        Simple random connected, undirected, weighted graph generation loosely inspired by the Barabási-Albert Model
    */
    public static Graph generateRandomGraph(int V, int E, int maxWeight){
        Random rand = new Random();
        Graph randomGraph = new Graph(V, E);
        for (int i = 1; i < V; i++){
            int weight = rand.nextInt(maxWeight) + 1;
            int destination = rand.nextInt(i);
            randomGraph.addEdge(i, destination, weight);
        }
        for (int i = V - 1; i < E; i++){
            int source = rand.nextInt(V);
            int destination = rand.nextInt(V);
            int weight = rand.nextInt(maxWeight) + 1;
            randomGraph.addEdge(source, destination, weight);
        }
        return randomGraph;
    }

    public String toDotString() {
        StringBuilder dotString = new StringBuilder();
        dotString.append("graph G {\n");
        for (int i = 0; i < V; i++) {
            for (int[] edge : adjacencyList[i]) {
                dotString.append(String.format("  %d -- %d [label=%d];\n", i, edge[0], edge[1]));
            }
        }
        dotString.append("}\n");
        return dotString.toString();
    }

    public void writeDotFile(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(toDotString());
            writer.close();
            System.out.println("Dot file saved as " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
