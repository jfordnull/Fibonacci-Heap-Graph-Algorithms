import java.util.ArrayList;
import java.util.HashMap;

public class FibHeapPriorityQueue implements PriorityQueue{
    ArrayList<FibHeapNode> roots;
    FibHeapNode minNode;
    HashMap<Integer, FibHeapNode> nodeMap;
    int upperBound;

    public FibHeapPriorityQueue() {
        roots = new ArrayList<>();
        minNode = null;
        nodeMap = new HashMap<>();
        upperBound = 0;
    }

    public boolean isEmpty() {
        return roots.isEmpty();
    }

    public void insert(int vertex, int distance){insertNode(new FibHeapNode(vertex, distance));}

    private void insertNode(FibHeapNode node) {
        node.parent = null;
        node.isLoser = false;
        if (minNode == null) {
            minNode = node;
        } else {
            if (node.distance < minNode.distance) {
                minNode = node;
            }
        }
        roots.add(node);
        nodeMap.put(node.key, node);
    }

    public int getDistance(int key){
        FibHeapNode node = nodeMap.get(key);
        return node.distance;
    }

    public PriorityQueueNode extractMin() {
        FibHeapNode z = minNode;
        if (z != null) {
            if (z.child != null) {
                for (FibHeapNode child : z.child) {
                    insertNode(child);
                }
            }
            roots.remove(minNode);
            if (roots.isEmpty()) {
                minNode = null;
            } else {
                consolidate();
                minNode = findMin();
            }
        }
        if(z != null){nodeMap.remove(z.key);}
        return new PriorityQueueNode(z.key, z.distance);
    }

    private FibHeapNode findMin() {
        FibHeapNode newMin = null;
        for (FibHeapNode node : roots) {
            if (newMin == null || node.distance < newMin.distance) {
                newMin = node;
            }
        }
        return newMin;
    }

    private void consolidate(){
        double phi = (1 + Math.sqrt(5)) / 2;
        int upperBound = (int)(Math.log(nodeMap.size()) / Math.log(phi));
        FibHeapNode[] degreeArray = new FibHeapNode[upperBound + 1];
        for (int i = 0; i <= upperBound; i++) {
            degreeArray[i] = null;
        }
        for (FibHeapNode node : roots) {
            int degree = node.degree;
            while (degreeArray[degree] != null) {
                FibHeapNode other = degreeArray[degree];
                if (node.distance > other.distance) {
                    FibHeapNode temp = node;
                    node = other;
                    other = temp;
                }
                heapLink(node, other);
                degreeArray[degree] = null;
                degree++;
            }
            degreeArray[degree] = node;
        }
        roots.clear();
        for (FibHeapNode node : degreeArray) {
            if (node != null) {
                insertNode(node);
            }
        }
    }

    private void heapLink(FibHeapNode parent, FibHeapNode child) {
        child.parent = parent;
        child.isLoser = false;
        parent.child.add(child);
        parent.degree++;
    }

    public void decreaseKey(int vertex, int distance) {
        FibHeapNode node = nodeMap.get(vertex);
        node.distance = distance;
        FibHeapNode parent = node.parent;
        if (parent != null && node.distance < parent.distance) {
            cut(node, parent);
            cascadingCut(parent);
        }
    }

    private void cut(FibHeapNode child, FibHeapNode parent) {
        parent.child.remove(child);
        insertNode(child);
        parent.degree--;
    }

    private void cascadingCut(FibHeapNode node) {
        FibHeapNode parent = node.parent;
        if (parent != null) {
            if (!node.isLoser) {
                node.isLoser = true;
            } else {
                cut(node, parent);
                cascadingCut(parent);
            }
        }
    }
}

class FibHeapNode {
    boolean isLoser;
    int distance, degree, key;
    FibHeapNode parent;
    ArrayList<FibHeapNode> child;

    public FibHeapNode(int key, int distance) {
        this.distance = distance;
        this.key = key;
        degree = 0;
        child = new ArrayList<>();
    }
}