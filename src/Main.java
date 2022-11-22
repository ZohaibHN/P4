import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Graph graph;
    static ArrayList<String> active;
    static ArrayList<String> ordering;

    public static boolean noEdge(String name) {
        boolean result = true;
        int num = graph.lookup(name);
        for (int i = 0; i < graph.getSize(); i++) {
            if (graph.isEdge(i, num) == true) {
                return false;
            }
        }
        return result;
    }

    public static void main(String args[]) {
        //String[] line = new String[];
        String name = args[0];
        active = new ArrayList<String>();
        ordering = new ArrayList<String>();
        File fi = new File("src/" + name);
        try {
            FileInputStream file = new FileInputStream(fi); //first input stream to read file
            FileInputStream fileTwo = new FileInputStream(fi); //second input stream to read file
            Scanner scan = new Scanner(file); //scanner to read file the first time
            Scanner secondScan = new Scanner(fileTwo); //Scanner to read file the second time
            int graphNum = Integer.parseInt(scan.nextLine()); //reads number to create graph size
            secondScan.nextLine(); //skips that number in the second scanner
            graph = new Graph<>(graphNum); //create the graph with the size read from file
            System.out.println(graphNum); //test case
            int i = 0;
            while(scan.hasNext()) { //read file with first scanner

                String line = scan.next(); //reads first class name
                //System.out.println(line + " " + i);
                graph.setValue(i, line); //create a node with class
                scan.nextLine(); //goes to next line
                i++;
            }
            while(secondScan.hasNextLine()) { //Second scanner to read the file to assign edges
                String firstClass = secondScan.next(); //reads first class in line
                //System.out.println(firstClass + " TESTTTT"); //test
                int loop = secondScan.nextInt(); //reads number of prerequisites following the first class name
                    for (int j = 0; j < loop; j++) { //loop to go through each number of prerequisites
                        String secondClass = secondScan.next(); //reads class that is prerequisite
                        //System.out.println(firstClass + " FIRST");
                        //System.out.println(secondClass + " SECONG");
                         graph.insertEdge(graph.lookup(secondClass), graph.lookup(firstClass));
                    }
            }
            //for (int j = 0; j < graph.getSize(); j++) {
            //    System.out.println(noEdge(String.valueOf(graph.getValue(j))) + " EDGE TEST");
            //}
            for (int j = 0; j < graph.getSize(); j++) {
                //System.out.println(graph.getValue(j) + "GGG");
                if (noEdge(String.valueOf(graph.getValue(j))) == true) {
                    active.add(String.valueOf(graph.getValue(j)));
                }
            }
            System.out.println(active.size() + " SIZE");

            while (!active.isEmpty()) {
                ordering.add(active.get(0));
                //System.out.println(active.get(0));
                for (int j = 0; j < graph.getSize(); j++) {
                    if (graph.isEdge(graph.lookup(active.get(0)), j) == true) {
                        graph.removeEdge(graph.lookup(active.get(0)), j);
                        if (noEdge(String.valueOf(graph.getValue(j))) == true) {
                            active.add(String.valueOf(graph.getValue(j)));
                        }
                    }
                }

                active.remove(0);
            }

            for (int j = 0; j < ordering.size(); j++) {
                //System.out.println(ordering.get(i));
            }


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
