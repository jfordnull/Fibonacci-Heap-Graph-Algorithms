public class Prim {
    public static PrimResults prim(Graph graph, String heapType){
        PriorityQueue priorityQueue;
        if (heapType.equals("Fibonacci")){priorityQueue = new FibonacciHeap();}
        else {priorityQueue = new MinHeap();}
        long startTimer = System.nanoTime();
        int V = graph.V;
        int[] parent = new int[V];
        boolean[] inMST = new boolean[V];
        priorityQueue.insert(0,0);
        for (int i = 1; i < V; i++){
            priorityQueue.insert(i, Integer.MAX_VALUE);}
        parent[0] = -1;
        while (!priorityQueue.isEmpty()){
            PriorityQueueNode minNode = priorityQueue.extractMin();
            int u = minNode.vertex;
            inMST[u] = true;
            for (int[] neighbor : graph.getAdjacentEdges(u)){
                int v = neighbor[0];
                if (!inMST[v] && neighbor[1] < priorityQueue.getDistance(v)){
                    parent[v] = u;
                    priorityQueue.decreaseKey(v, neighbor[1]);
                }
            }
        }
        long stopTimer = System.nanoTime();
        double durationInSeconds = (stopTimer - startTimer) / 1e9;
        System.out.println("Prim's implemented with a min-heap priority queue took: " +
                durationInSeconds + " seconds");
        System.out.println("For graph with V = " + graph.V + " and E = " + graph.E);
        return new PrimResults(parent,durationInSeconds);
    }
}

class PrimResults{
    public PrimResults(int[] parent, double duration){this.parent = parent; durationInSeconds = duration;}
    public int[] parent;
    public double durationInSeconds;
}
