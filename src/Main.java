public class Main {
    public static void main(String[] args){
        TestExecutionTimes.writeDijkstraTimesToCSV(25, 1000);
        TestExecutionTimes.writeDijkstraTimesToCSV(25,9000);
        TestExecutionTimes.writeDijkstraTimesToCSV(25,17000);
        TestExecutionTimes.writeDijkstraTimesToCSV(25,25000);
        TestExecutionTimes.writePrimTimesToCSV(25,1000);
        TestExecutionTimes.writePrimTimesToCSV(25,9000);
        TestExecutionTimes.writePrimTimesToCSV(25,17000);
        TestExecutionTimes.writePrimTimesToCSV(25,25000);
    }
}
