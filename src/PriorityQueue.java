public interface PriorityQueue {
    void insert(int vertex, int priority);
    PriorityQueueNode extractMin();
    void decreaseKey(int vertex, int newPriority);
    boolean isEmpty();
    int getDistance(int vertex);
}
