import java.util.Arrays;

public class Dijkstra {
    public static DijkstraResult dijkstra(Graph graph, int source, String heapType){
        PriorityQueue priorityQueue;
        if (heapType.equals("Fibonacci")){priorityQueue = new FibHeapPriorityQueue();}
        else {priorityQueue = new MinHeapPriorityQueue();}
        long startTimer = System.nanoTime();
        int[] distances = new int[graph.V];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        for (int i = 0; i < graph.V; i++){priorityQueue.insert(i, distances[i]);}
        while(!priorityQueue.isEmpty()){
            PriorityQueueNode minNode = priorityQueue.extractMin();
            int u = minNode.vertex;
            for (int[] neighbor : graph.getAdjacentEdges(u)){
                int v = neighbor[0];
                int w = neighbor[1];
                if (distances[u] != Integer.MAX_VALUE && distances[u] + w < distances[v]){
                    distances[v] = distances[u] + w;
                    priorityQueue.decreaseKey(v, distances[v]);
                }
            }
        }
        long stopTimer = System.nanoTime();
        double durationInSeconds = (stopTimer - startTimer) / 1e9;
        System.out.println("Dijkstra's implemented with " + heapType + " took " + durationInSeconds + " seconds");
        System.out.println("for graph with V = " + graph.V + " and E = " + graph.E);
        return new DijkstraResult(distances, durationInSeconds);
    }
}

class DijkstraResult{
    public DijkstraResult(int[] distances, double duration){
        this.distances = distances;
        durationInSeconds = duration;
    }
    public int[] distances;
    public double durationInSeconds;
}