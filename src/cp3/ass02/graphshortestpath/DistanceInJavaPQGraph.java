package cp3.ass02.graphshortestpath;

import java.util.*;

public class DistanceInJavaPQGraph extends Graph{

    /**
     Priority queue for quick access to smallest distance
     */
    protected PriorityQueue<Distance> distances;

    /**
     * Add the given vertex to the graph.  Allows a vertex to be added that might not be connected.
     * Need to check if the Vertex v already exists to stop duplicates and add only if it does not exist.
     *
     * @param v the vertex to be added
     */
    @Override
    void addVertex(Vertex v) {
        if(!vertices.contains(v)){
            vertices.add(v);
            adjList.add(new HashMap<>());
        }
    }

    /**
     * Add edge v-w.  Will also add Vertex v and w if they do not already exist.
     * Need to check if the v and w already exists to stop duplicates and add only if it does not exist.
     *
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     */
    @Override
    void addEdge(int v, int w, int weight) {
        Vertex vr = new Vertex(v);
        Vertex wr = new Vertex(w);
        if(!vertices.contains(vr)){
            vertices.add(vr);
            adjList.add(new HashMap<>());
        }
        if(!vertices.contains(wr)){
            vertices.add(wr);
            adjList.add(new HashMap<>());
        }
        int temp = vertices.indexOf(wr);
        adjList.get(vertices.indexOf(vr)).put(vertices.get(temp),weight);
    }

    /**
     * Neigbours of vertex v in lexicographic order.  The method will return
     * null if there are no adjacent vertices.
     *
     * @param v the vertex to find the neighbours of.
     * @return the list of adjacent vertices or null if no adjacent vertices.
     */
    Map<Vertex,Integer> adjacentTo(Vertex v) {
        return adjList.get(vertices.indexOf(v));
    }

    /**
     * Neigbours of vertex v in lexicographic order.  The method will return
     * null if there are no adjacent vertices.
     *
     * @param index the index in adjList to find the neighbours of.
     * @return the list of adjacent vertices or null if no adjacent vertices.
     */
    @Override
    Map<Vertex,Integer> adjacentTo(int index) {
        return adjList.get(index);
    }


    /**
     * Gets the vertex in the graph with the label v
     *
     * @param v in an int
     * @return the vertex v if in the graph
     */
    @Override
    Vertex getVertex(int v) {
        for(Vertex vt : vertices){
            if(vt.getLabel().equals(v)){
                return vt;
            }
        }
        return null;
    }


    /**
     * Prints the vertices and edges in this graph
     *
     */
    public void print(){
        for(Vertex vt : vertices){
            System.out.println("Node: " + vt.getLabel());
            System.out.print("Edges: ");
            Map<Vertex,Integer> m = adjacentTo(vt);
            for(Map.Entry<Vertex,Integer> e : m.entrySet()){
                System.out.print(e.getKey() + "(w:" + e.getValue() + "), ");
            }
            System.out.println();
        }
    }


    /**
     * Runs Dijkstra's to populate the Dijkstra Map
     * 1. sets states of each vertex to default values
     * 2. sets the distances for all edge nodes
     * 3. finds the next closest node
     *
     * @param s is the source vertex for the Dijkstra map
     */
    public void runDijkstra(int s){
        // reset all the Dijkstra variables in each Vertex
        // add a new Distance object for each Vertex to the distances PriorityQueue
        // this will be an O(V) operation
        Vertex v = null;
        distances = new PriorityQueue<>();
        for(int i = 0; i< vertices.size(); i++){
            Vertex vt = vertices.get(i);
            vt.distance = MAX;
            vt.known = false;
            vt.predecessor = null;
            vt.index = i;
            // this could be a O(logV) operation, but since they all have the same value, there should be no reordering? so really O(1)?
            distances.add(new Distance(vt, MAX));
            if (vt.getLabel() == s) {
                v = vt;
            }
        }
        v.distance = 0;

        // set distance for each unknown edge of current
        // iterates over each V
        for(int i = 0; i < vertices.size(); i++) {
            v.known = true;
            // the adjacentTo() function calls the get() method of the adjList Edge HashMap which takes O(1) time
            // this is enabled through each Vertex holding it's index number in the vertices data structure
            Map<Vertex, Integer> m = adjacentTo(v.index);
            if(!m.isEmpty()) {
                // Each edge will only be processed once (all the edges with current as source are unique) so only adds E to the total across all loops
                for (Map.Entry<Vertex, Integer> e : m.entrySet()) {
                    // adding an element to a a PriorityQueue is worst case O(logV) at the start
                    // however, as we aren't removing old distances, the size of the heap will increase to E
                    // so this operation will degenerate to O(logE) at the worst, which it mostly will be for these operations since we're usually adding smaller values
                    // this will occur once for each edge, so this whole operation is O(ElogE)
                    // everything else is an assignment or simple getter op, so O(1) and doesn't scale with V or E
                    Vertex w = e.getKey();
                    if (!w.known) {
                        if (v.distance + e.getValue() < w.distance) {
                            w.distance = v.distance + e.getValue();
                            w.predecessor = v;
                            distances.add(new Distance(w,w.distance));
                        }
                    }
                }
            }
            // choose the next closest Vertex to be the current Vertex, for which known = false
            // will poll the smallest value from the PriorityQueue, which takes O(logV) usually
            // but as we aren't removing old distances, will degenerate to O(logE)
            // so for V loops, this will add O(VlogE) to the total
            while(v.known && !distances.isEmpty()) {
                v = distances.poll().vertex;
            }
        }
    }

    /**
     * Print the shortest paths calculated in runDijkstra()
     * Uses the predecessor to work backwards through the Map
     *
     */
    public void printShortestPaths(){
        TreeMap<Integer, String> m = new TreeMap<>();
        // this will be an O(VE) operation
        for(Vertex current : vertices) {
            String str = "shortest path to " + current.getLabel() + ": ";
            if (current.distance == 100000) {
                str += "NO PATH";
                m.put(current.getLabel(), str);
                continue;
            }
            Stack<Integer> s = new Stack<>();
            Vertex temp = current;
            // push each Vertex label onto a stack as you move through predecessors back to 0
            // for each V, may need to do this up to E times, so O(VE), but usually a lot less, closer to O(V)
            while (temp.predecessor != null) {
                s.push(temp.predecessor.getLabel());
                temp = temp.predecessor;
            }
            // move forwards along the path, adding the label of each edge
            // for each V, may need to do this up to E times, so O(VE), but usually a lot less, closer to O(V)
            while (!s.empty()) {
                str += s.pop() + " ";
            }
            str += current.getLabel() + ": cost = " + current.distance;
            // this will be an O(logV) operation, inserting into a TreeMap
            m.put(current.getLabel(), str);
        }
        // this will be an O(V) operation
        for(String s : m.values()){
            System.out.println(s);
        }
    }
}



