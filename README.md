# Fibonacci Heap Graph Algorithms
### Optimizing Dijkstra's Shortest Path & Prim's Minimum Spanning Tree<br><br>
![](Documentation/GraphAnimation.gif)<br>
_A randomly generated connected, undirected, weighted graph at three levels of magnification. Written in .DOT and visualized using the open-source graphing utility Gephi. |V| = 5 x 10<sup>4</sup> and |E| = 1 x 10<sup>9</sup>_<br>

In 1984, Michael L. Fredman and Robert E. Tarjan proposed a data structure they called a Fibonacci heap: a forest of heap-ordered trees, which they suggested could implement a priority queue and achieve the best-known time-complexity for certain operations, namely a method to decrease the key associated with a particular node in the queue in amortized constant time. This is a significant optimzation for network problems involving dense graphs; In the worst-case, this method is needed once for every e in E during the "Relaxation" phase of both Dijkstra and Prim's algorithms. 

Despite its theoretical improvement over a binary heap priority queue, the constant factors and space-complexity associated with the Fibonacci heap have marred it with a reputation for being impractical. As Cormen et. al note in _Introduction to Algorithms_, Fibonacci heaps are "predominantly of theoretical interest." However, over the course of this project I found that Fibonacci heaps are of empirical use in optimizing spanning tree algorithms for graphs where |E| is significantly larger than |V|. I also found Fibonacci heaps easier to implement in a modern programming environment than their reputation would suggest. You'll find a brief overview of my implementation below, as well as my analysis of the asymptotic behavior we should expect. This includes an amortized analysis of the Fibonacci heap using the potential function given by Cormen et. al.

### Graph Representation

My graphs are implemented as an adjacency list. Each vertex corresponds with an index in an array where a singly-linked list stores its incident edges. To ensure the graph is undirected, I add an edge as a pair of edges flowing in both directions. If you're unfamiliar with data structures for graphs, you can read about the difference between an adjacency list and an adjacency matrix [here](https://www.geeksforgeeks.org/comparison-between-adjacency-list-and-adjacency-matrix-representation-of-graph/).

### Random Graph Generation

My approach to the generation of random weighted, connected, undirected graphs resembles the Barabási-Albert model, though I was unaware of this when I wrote the code. As I build the graph, I ensure connectivity by selecting a destination for the edge to be added from the set of vertices I've visited. This has the same side effect as the Barabási-Albert model: scale-free networks. The distribution of edges favors hubs at the start of the graph and grows sparser as we fan out.  

### Dijkstra's Shortest Path and Prim's MST

My implementation of Dijkstra's returns an array containing the set of shortest-path distances for each node in the graph from the source. If desired, it could be rewritten to return the list of edges contained in the shortest path tree for visualization without a change in worst-case time-complexity. A high-level description of the algorithm, followed by the code:

1.  Assign a cost or tentative distance from source to every vertex in the graph. Let cost associated with source = 0 and cost associated with every other vertex = ∞
2.  “Relax” all vertices adjacent to the current node. If distance to reach neighbors through the current node is less than the known cost, update accordingly
3.  Choose the min-cost vertex as next current node
4.  Repeat 2 and 3 until our queue is empty 

```
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
```
_u is the current node, v is the neighbor node, and w is the weight of their shared edge_.

