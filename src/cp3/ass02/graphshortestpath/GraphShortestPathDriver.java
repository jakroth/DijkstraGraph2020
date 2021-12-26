package cp3.ass02.graphshortestpath;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.jdom2.JDOMException;

/**
 *
 * @author lewi0146
 */
public class GraphShortestPathDriver {

    static String[] files = {
            /*
            "data/graphs/graphSpecExample.graphml",
            "data/graphs/graphTutorialExample.graphml",
            "data/graphs/graph10.graphml",
            "data/graphs/graph100A.graphml",
            "data/graphs/graph1000.graphml",
            */

            "data/randomgraphs/random_v10_e10_w50.graphml",
            "data/randomgraphs/random_v100_e100_w50.graphml",
            "data/randomgraphs/random_v100_e500_w50.graphml",
            "data/randomgraphs/random_v100_e1000_w50.graphml",
            "data/randomgraphs/random_v100_e5000_w50.graphml",
            "data/randomgraphs/random_v100_e9900_w50.graphml",
            "data/randomgraphs/random_v1000_e1000_w50.graphml",
            "data/randomgraphs/random_v1000_e5000_w50.graphml",
            "data/randomgraphs/random_v1000_e10000_w50.graphml",
            "data/randomgraphs/random_v1000_e50000_w50.graphml",
            "data/randomgraphs/random_v1000_e100000_w50.graphml",
            "data/randomgraphs/random_v1000_e500000_w50.graphml",
            "data/randomgraphs/random_v1000_e999000_w50.graphml",

            /*
            "data/braingraphs/brain.graphml",
            "data/braingraphs/brain_359.graphml",
            "data/braingraphs/brain_8790.graphml"
            */
    };

    static String filename;
    static int graphType;
    static int sourceVertex;
    static boolean printInfo;
    static boolean printPaths;

    /**
     *
     * The main method intialises some static variables then launches the various tests
     *
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, JDOMException {

        sourceVertex = 0;
        printInfo = false;
        printPaths = false;

        // main loop to test implementations
        // loops over the filenames in files
        for (String file : files) {
            // loops over the 4 different graph types
            for (int j = 1; j <= 4; j++) {

                // set the file for this loop
                filename = file;

                // set the Graph type for this loop
                graphType = j;

                // Test time for this implementation
                //timeTest(1); // initial test to warm things up
                //timeTest(10);

                // Test memory for this implementation
                memTest();
            }
        }
    }

    /**
     * Time test for each Graph implementation
     *
     */
    public static void timeTest(int loops) throws FileNotFoundException, IOException, JDOMException {

        System.out.println("timeTest");
        System.out.println(filename);
        System.out.println("Graph: " + graphType);

        // instantiate the profiler object
        GraphProfiler profiler = new GraphProfiler();

        // run loops
        profiler.avgReset(loops);
        for(int i = 0; i < loops; i++){
            // build graph
            Graph g = GraphBuilder.buildFromGraphML(filename, graphType);

            profiler.avgTic();
            // run Dijkstra's algorithm
            g.runDijkstra(sourceVertex);
            profiler.avgToc();

            // print graph
            if(printInfo) g.print();

            // print shortest paths to source node
            if(printPaths) g.printShortestPaths();

            g = null;
            profiler.takeOutGarbage();

        }
        System.out.println("Loops: " + loops);
        System.out.println("Average time (μs): " + profiler.avgCalc()/1000);
        System.out.println("Average time (ms): " + GraphProfiler.nsToms(profiler.avgCalc()));
        System.out.println();
    }

    /**
     * Memory test for each Graph implementation
     *
     */
    public static void memTest() throws FileNotFoundException, IOException, JDOMException {

        System.out.println("memTest");
        System.out.println(filename);
        System.out.println("Graph: " + graphType);

        // instantiate the profiler object
        GraphProfiler profiler = new GraphProfiler();

        // build graph
        profiler.ticMem();
        Graph g = GraphBuilder.buildFromGraphML(filename, graphType);
        long graphMemory = profiler.tocMem();

        // run Dijkstra's algorithm
        profiler.ticMem();
        g.runDijkstra(sourceVertex);
        long dijkstraMemory = profiler.tocMem();

        // print graph
        if(printInfo) g.print();

        // print shortest paths to source node
        if(printPaths) g.printShortestPaths();

        g = null;
        profiler.takeOutGarbage();

        System.out.println("Graph memory (kB): " + graphMemory/1000);
        System.out.println("Dijkstra memory (kB): " + dijkstraMemory/1000);
        System.out.println("Total memory (kB): " + (graphMemory + dijkstraMemory)/1000);
        System.out.println();
    }
}
