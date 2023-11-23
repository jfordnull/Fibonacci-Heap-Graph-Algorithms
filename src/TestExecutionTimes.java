import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestExecutionTimes {
    public static void writeDijkstraTimesToCSV(int G, int V){
        String csvFile = "AverageDijkstraTimes2.csv";
        int[] edges = edgesPerDensity(V);
        double density = 0.02;
        double[] minHeapAvgPerDensity = new double[10];
        double[] fibHeapAvgPerDensity = new double[10];
        for (int i = 0; i < 10; i++){
            double minHeapSum = 0.0;
            double fibHeapSum = 0.0;
            for (int j = 0; j < G; j++){
                Graph graph = Graph.generateRandomGraph(V, edges[i], 50);
                minHeapSum += Dijkstra.dijkstra(graph, 0, "Min-heap").durationInSeconds;
                fibHeapSum += Dijkstra.dijkstra(graph, 0, "Fibonacci").durationInSeconds;
            }
            minHeapAvgPerDensity[i] = minHeapSum/G;
            fibHeapAvgPerDensity[i] = fibHeapSum/G;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true))){
            StringBuilder data = new StringBuilder("V" + "," + "D" + "," + "E" + "," + "Min-Heap Average" + "," + "FibHeap Average" + "\n");
            for (int i = 0; i < 10; i++){
                data.append(V).append(",").append(density).append(",").append(edges[i]).append(",").append(minHeapAvgPerDensity[i]).
                        append(",").append(fibHeapAvgPerDensity[i]).append("\n");
                density += .11;
            }
            writer.write(String.valueOf(data));
            System.out.println("CSV File Generated");
        } catch(IOException e){e.printStackTrace();}
    }

    private static int[] edgesPerDensity(int V){
        double density = .002;
        int[] edgesPerGraphIncrement = new int[10];
        for (int i = 0; i < 10; i++){
            edgesPerGraphIncrement[i] = (int)((density * (V * (V-1)))/2);
            density += .11;
        }
        return edgesPerGraphIncrement;
    }
}
