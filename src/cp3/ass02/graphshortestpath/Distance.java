package cp3.ass02.graphshortestpath;

public class Distance implements Comparable<Distance>{
    int distance;
    Vertex vertex;

    public Distance(Vertex v, int d){
        vertex = v;
        distance = d;
    }

    @Override
    public int compareTo(Distance d) {
        return distance - d.distance;
    }
}
