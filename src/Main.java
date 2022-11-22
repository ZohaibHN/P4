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
        boolean result = false;
        int num = graph.lookup(name);
        for (int i = 0; i < graph.getSize(); i++) {
            if (graph.isEdge(i, num) == true) {
                result = false;
            } else {
                result = true;
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
            FileInputStream file = new FileInputStream(fi);
            FileInputStream fileTwo = new FileInputStream(fi);
            Scanner scan = new Scanner(file);
            Scanner secondScan = new Scanner(fileTwo);
            int graphNum = Integer.parseInt(scan.nextLine());
            secondScan.nextLine();
            graph = new Graph<>(graphNum);
            System.out.println(graphNum);
            int i = 0;
            while(scan.hasNext()) {

                String line = scan.next();
                //System.out.println(line + " " + i);
                graph.setValue(i, line);
                scan.nextLine();
                i++;
            }
            while(secondScan.hasNextLine()) {
                String firstClass = secondScan.next();
                //System.out.println(firstClass);
                int loop = secondScan.nextInt();
                    for (int j = 0; j < loop; j++) {
                        graph.insertEdge(graph.lookup(secondScan.next()), graph.lookup(firstClass));
                        //System.out.println(secondScan.next() + " LOOPER");
                    }
            }
            for (int j = 0; j < graph.getSize(); j++) {
                //System.out.println(graph.getValue(j) + "GGG");
                if (noEdge(String.valueOf(graph.getValue(j))) == true) {
                    active.add(String.valueOf(graph.getValue(j)));
                }
            }
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
