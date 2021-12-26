package cp3.ass02.graphshortestpath;

import java.util.*;

public class DistanceInMapGraph extends Graph {


    /**
     * A map to maintain the state of the Vertex objects represented in this Graph.
     * A Vertex state is captured in the DInfo class
     * Used for Dijkstra's Algorithm
     *
     * @see DInfo
     */
    protected Map<Integer,DInfo> dijkstraMap = new HashMap<>();

    public void clearState(){
        dijkstraMap.clear();
    }

    public void setState(Vertex v, DInfo state){
        dijkstraMap.put(v.getLabel(), state);
    }

    public DInfo getState(Vertex v){
        if (dijkstraMap.containsKey(v.getLabel())){
            return dijkstraMap.get(v.getLabel());
        }
        // if the Vertex v has no state, return null
        return null;
    }



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
     * Add edge v-w. Will also add Vertex v and w if they do not already exist.
     * Need to check if the v and w already exists to stop duplicates and add only if it does not exist.
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     */
    void addEdge(int v, int w, int weight){
        addEdge(new Vertex(v), new Vertex(w), weight);
    }

    /**
     * Add edge v-w.  Will also add Vertex v and w if they do not already exist.
     * Need to check if the v and w already exists to stop duplicates and add only if it does not exist.
     *
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     */
    void addEdge(Vertex v, Vertex w, int weight) {
        if(!vertices.contains(v)){
            vertices.add(v);
            adjList.add(new HashMap<>());
        }
        if(!vertices.contains(w)){
            vertices.add(w);
            adjList.add(new HashMap<>());
        }
        adjList.get(vertices.indexOf(v)).put(w,weight);
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
     *
     * @param source is the source vertex for the Dijkstra map
     */
    void runDijkstra(int source){
        runDijkstra(new Vertex(source));
    }

    /**
     * Runs Dijkstra's to populate the Dijkstra Map
     * 1. Clears the map
     * 2. sets states of each vertex to default values
     * 3. sets the distances for all edge nodes
     * 4. finds the next closest node
     *
     * @param s is the source vertex for the Dijkstra map
     */
    public void runDijkstra(Vertex s){
        // clear any existing map entries
        clearState();
        // set all DInfo data objects to default values
        // this will be an O(V) operation
        for(int i = 0; i< vertices.size(); i++){
            setState(vertices.get(i),new DInfo());
            vertices.get(i).index = i;
        }

        // setup some variables, and the initial current vertex
        Vertex current = s;
        DInfo v = getState(current);
        v.distance=0;

        // set distance for each unknown edge of current
        // iterates over each V
        for(int i = 0; i < vertices.size(); i++) {
            v.known = true;
            // the adjacentTo() function calls the get() method of the adjList Edge HashMap which takes O(1) time
            // this is enabled through each Vertex holding it's index number in the vertices data structure
            Map<Vertex, Integer> m = adjacentTo(current.index);
            if (!m.isEmpty()) {
                // Each edge will only be processed once (all the edges with current as source are unique) so only adds E to the total for all loops
                for (Map.Entry<Vertex, Integer> e : m.entrySet()) {
                    // processing time will vary, but since dijkstraMap is a hashmap, the get() method will be O(1)
                    // everything else is an assignment or simple getter op, so this whole operation is O(1), doesn't scale with V or E
                    DInfo w = getState(e.getKey());
                    if (!w.known) {
                        if (v.distance + e.getValue() < w.distance) {
                            w.distance = v.distance + e.getValue();
                            w.predecessor = current.getLabel();
                        }
                    }
                }
            }
            // choose the next closest Vertex to be the current Vertex, for which known = false
            // will loop over all the vertices to find the minimum, so this will take O(V) time
            // so for V loops, this will add O(V^2) to the total
            int min = MAX + 1;
            int next = -1;
            for (Map.Entry<Integer, DInfo> e : dijkstraMap.entrySet()) {
                DInfo n = e.getValue();
                if(!n.known){
                    if (n.distance < min) {
                        min = n.distance;
                        next = e.getKey();
                    }
                }
            }
            // retrieves the vertex associated with the next integer and the DInfo associated with the vertex
            // this is an O(V) operation at worst
            // the if statement avoids running the last loop, when there is no next
            if(next != -1){
                current = getVertex(next);
                v = getState(current);
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
        for(int i = 0; i < vertices.size(); i++){
            Vertex current = vertices.get(i);
            String str = "shortest path to " + current.getLabel() + ": ";
            DInfo d = getState(current);
            if(d.distance == 100000){
                str += "NO PATH";
                m.put(current.getLabel(), str);
                continue;
            }
            Stack<Integer> s = new Stack<>();
            int cost = d.distance;
            // push each Vertex label onto a stack as you move through predecessors back to 0
            // for each V, may need to do this up to E times, so O(VE), but usually a lot less, closer to O(V)
            while(d.predecessor != -1){
                s.push(d.predecessor);
                d = dijkstraMap.get(d.predecessor);
            }
            // move forwards along the path, adding the label of each edge
            // for each V, may need to do this up to E times, so O(VE), but usually a lot less, closer to O(V)
            while(!s.empty()) {
                str += s.pop() + " ";
            }
            str += current.getLabel() + ": cost = " + cost;
            // this will be an O(logV) operation, inserting into a TreeMap
            m.put(current.getLabel(),str);
        }
        // this will be an O(V) operation
        for(String s : m.values()){
           System.out.println(s);
        }
    }
}

