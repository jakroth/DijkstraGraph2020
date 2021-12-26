package cp3.ass02.graphshortestpath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author lewi0146
 */
public abstract class Graph {

    /**
     Lists to maintain the vertices and a list of adjacent vertices with weights for each edge
     */
    protected List<Vertex> vertices = new ArrayList<>();
    protected List<Map<Vertex,Integer>> adjList = new ArrayList<>();

    public static final int MAX = 100000;


    /**
     * Add the given vertex to the graph.  Allows a vertex to be added that might not be connected.
     * Need to check if the Vertex v already exists to stop duplicates and add only if it does not exist.
     * @param v the vertex to be added
     */
    abstract void addVertex(Vertex v);


    /**
     * Add edge v-w. Will also add Vertex v and w if they do not already exist.
     * Need to check if the v and w already exists to stop duplicates and add only if it does not exist.
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     */
    abstract void addEdge(int v, int w, int weight);



    /**
     * Neigbours of vertex v in lexicographic order.  The method will return
     * null if there are no adjacent vertices.
     * @param v the vertex to find the neighbours of.
     * @return the list of adjacent vertices or null if no adjacent vertices.
     */
    abstract Map<Vertex, Integer> adjacentTo(int index);



    /**
     * Gets the vertex in the graph with the label v
     * @param v
     * @return
     */
    abstract Vertex getVertex(int v);


    /**
     * Prints the vertices and edges in this graph
     *
     *
     */
    abstract void print();


    /**
     * Runs Dijkstra's to populate the Dijkstra Map
     *
     * @param source is the source vertex for the Dijkstra map
     */
    abstract void runDijkstra(int source);


    /**
     * Print the shortest paths calculated in runDijkstra()
     *
     */
    abstract void printShortestPaths();

}


