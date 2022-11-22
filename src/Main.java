import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Graph graph; //initial graph
    static ArrayList<String> active; //active set made as ArrayList
    static ArrayList<String> ordering;//ordering made as ArrayList

    public static boolean noEdge(String name) { //checks if node has an edge or not
        boolean result = true; //initial boolean result
        int num = graph.lookup(name); //reference to index of node
        for (int i = 0; i < graph.getSize(); i++) { //for loop to go through the whole graph
            if (graph.isEdge(i, num) == true) { //checks if edge is going into node that was passed into method
                return false; //returns false and breaks the loop
            }
        }
        return result; //returns true if loop goes all the way through
    }

    public static boolean noedgeGraph() { //method to check if graph has edges
        boolean result = true; //initial boolean result
        for (int i = 0; i < graph.getSize(); i++) { //nested for loop to go through graph
            for (int j = 0; j < graph.getSize(); j++) {
                if (graph.isEdge(i, j) == true) { //checks if there is an edge
                    return false; //returns false to break the loop
                }
            }
        }
        return result; //returns true if loop goes through all the way
    }

    public static void main(String args[]) {
        //String[] line = new String[];
        String name = args[0]; //reads file name from program parameter
        active = new ArrayList<String>(); //declares active arraylist
        ordering = new ArrayList<String>(); //declares ordering arraylist
        File fi = new File("src/" + name); //filepath
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
                if (noEdge(String.valueOf(graph.getValue(j))) == true) { //to add nodes with no edge into active
                    active.add(String.valueOf(graph.getValue(j)));
                }
            }

            while (!active.isEmpty()) { //while active is not empty
                ordering.add(active.get(0)); //adds index of active in ordering

                for (int j = 0; j < graph.getSize(); j++) { //for loop to through entire graph
                    if (graph.isEdge(graph.lookup(active.get(0)), j) == true) { //checks if node has an edge going into node
                        graph.removeEdge(graph.lookup(active.get(0)), j); //removes edge
                        if (noEdge(String.valueOf(graph.getValue(j))) == true) { //checks if second node has an edge going into it
                            active.add(String.valueOf(graph.getValue(j))); //adds it to active if true
                        }
                    }
                }
                active.remove(0); //removes initial node from the active list
            }
            if (noedgeGraph() == true) { //checks if there are edges in the graph
                for (int j = 0; j < ordering.size(); j++) { //prints out the ordering list
                    System.out.println(ordering.get(j));
                }
            } else { //if there are edges in the graph
                System.out.println("Courses impossible to complete:"); //print statement
                for (int j = 0; j < graph.getSize(); j++) { //goes through entire graph
                   if (noEdge(String.valueOf(graph.getValue(j))) == false) { //prints out the nodes that have edges going into them
                       System.out.println(String.valueOf(graph.getValue(j)));
                   }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
