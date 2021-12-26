package cp3.ass02.graphshortestpath;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author lewi0146
 */
public class Vertex implements Comparable<Vertex> {

    /**
     * The uniquely identifying label for the Vertex
     */
    private Integer label;

    /**
     * Information for Dijkstra's Algorithm
     * These values are always relative to a source Vertex
     */
    public boolean known = false;
    public int distance = Graph.MAX;
    public Vertex predecessor = null;
    public int index;
    /**
     * Constructor
     */
    public Vertex(int label){
        this.label = label;
    }

    /**
     * Returns the hash code for this object, based on the label (calls <code>this.label.hashCode()</code>.
     * @return
     */
    @Override
    public int hashCode() {
        return this.label.hashCode();
    }

    /**
     * This equals compares this Vertex to the given Object and if it is Vertex it compares based on the label.
     * @param obj the object to compare to this Vertex
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;

        return Objects.equals(this.label, other.label);
    }


    /**
     * Compares this Vertex to another Vertex based on the label of the objects.
     * @param t
     * @return
     */
    @Override
    public int compareTo(Vertex t) {
        return this.label.compareTo(t.label);
    }

    @Override
    public String toString() {
        // a use value to see the "true identity" of an object
        //return String.valueOf(System.identityHashCode(this));
        return this.label.toString();
    }

    /**
     * Gets the uniquely identifying label of this Vertex.
     * @return
     */
    public Integer getLabel() {
        return label;
    }
}


