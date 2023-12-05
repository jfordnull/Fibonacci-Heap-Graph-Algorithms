public class Main {
    public static void main(String[] args){
        int G = 25;
        if (args.length > 0) {
            try {G = Integer.parseInt(args[0]);}
            catch (NumberFormatException e) {System.err.println("Invalid integer");}
        }
    }

    private static void testRunningTimes(int G){
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
