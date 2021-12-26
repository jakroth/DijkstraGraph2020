package cp3.ass02.graphshortestpath;

public class DInfo {
    /**
     * Information for Dijkstra's Algorithm
     * These values are always relative to a source Vertex
     */
    public boolean known = false;
    public int distance = 100000;
    public int predecessor = -1;
}
