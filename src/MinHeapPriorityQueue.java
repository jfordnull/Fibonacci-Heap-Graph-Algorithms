import java.util.ArrayList;
import java.util.HashMap;

//Min-Heap implementation of a priority queue
public class MinHeapPriorityQueue{

    /*
        Binary tree bijection. Relative array positions represent child-parent relationships
        Left-Child = 2i + 1
        Right-Child = 2i + 2
        Parent = (i - 1) / 2
    */
    ArrayList<PriorityQueueNode> heap;

    /*
        HashMap will give us O(1) lookup time for any vertex
        key: vertex ; value: array position
    */
    HashMap<Integer, Integer> vertexToIndex;

    public MinHeapPriorityQueue(){
        heap = new ArrayList<>();
        vertexToIndex = new HashMap<>();
    }

    public void insert(int vertex, int distance){
        heap.add((new PriorityQueueNode(vertex,distance)));
        vertexToIndex.put(vertex, heap.size() - 1);
        heapifyUp(heap.size() - 1);
    }

    public PriorityQueueNode extractMin(){
        PriorityQueueNode minNode = heap.get(0);
        PriorityQueueNode lastNode = heap.get(heap.size()-1);
        heap.set(0, lastNode);
        heap.remove(heap.size()-1);
        vertexToIndex.remove(minNode.vertex);
        if(!heap.isEmpty()){
            vertexToIndex.put(heap.get(0).vertex, 0);
            heapifyDown(0);
        }
        return minNode;
    }

    public int getDistance(int vertex){
        Integer index = vertexToIndex.get(vertex);
        return heap.get(index).distance;
    }

    //O(log n)
    public void decreaseKey(int vertex, int newDistance){
        Integer index = vertexToIndex.get(vertex);
        if (index != null){
            heap.get(index).distance = newDistance;
            heapifyUp(index);
        }
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }

    //O(log n)
    private void heapifyUp(int i){
        while (i > 0){
            int parentIndex = (i - 1) / 2;
            if (heap.get(parentIndex).distance <= heap.get(i).distance){
                break;
            }
            swap(parentIndex,i);
        }
    }

    //O(log n)
    private void heapifyDown(int i){
        int leftChildIndex = (2 * i) + 1;
        int rightChildIndex = leftChildIndex + 1;
        int smallestKey = i;
        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).distance < heap.get(smallestKey).distance){
            smallestKey = leftChildIndex;
        }
        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).distance < heap.get(smallestKey).distance){
            smallestKey = rightChildIndex;
        }
        if (smallestKey != i){
            swap(i, smallestKey);
            heapifyDown(smallestKey);
        }
    }

    private void swap(int i, int j){
        PriorityQueueNode temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
        vertexToIndex.put(heap.get(i).vertex, i);
        vertexToIndex.put(heap.get(j).vertex, j);
    }
}

class PriorityQueueNode{
    int vertex;
    int distance;
    public PriorityQueueNode(int vertex, int distance){
        this.vertex = vertex;
        this.distance = distance;
    }
}