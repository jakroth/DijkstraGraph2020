package cp3.ass02.graphshortestpath;

public class MyPriorityQueue {

    Distance[] array;
    int size = 0;

    /**
     * Constructor for this data structure
     * Will set up the array as the max needed to hold Distance variables for the given Graph
     *
     * @param edges is the sum of edges and vertices for this graph
     */
    public MyPriorityQueue(int edges){
        array = new Distance[edges];
    }

    /**
     * Add the given Distance object to the array list by calling the siftUp method
     *
     * @param d a new Distance object to add
     */
    public void add(Distance d){
        size++;
        siftUp(size-1, d);
    }

    /**
     * Add the given Distance object to the array list
     * Needs to repeatedly compare with the parent while it is smaller
     * Maintains partial ordering of the PriorityQueue
     *
     * @param i the index into the array, representing parents/children in binary tree
     * @param d a Distance object to add
     */
    private void siftUp(int i, Distance d){
        while(i > 0){
            int parent = (i - 1)/2;         // use logical model to know where parent is
            Distance temp = array[parent];
            if(d.compareTo(temp) >= 0){     // if d is not smaller than the parent...
                break;                      // ...break...
            }
            array[i] = temp;                // ...otherwise switch the parent into the child...
            i = parent;                     // ...and keep moving up
        }
        array[i] = d;                       // if d is not smaller than parent, save d at current index
    }

    /**
     * Remove the smallest Distance object from the array list
     * @return the smallest Distance object
     */
    public Distance poll(){
        size--;
        Distance d = array[0];
        siftDown();
        return d;
    }
    /**
     * Sift down by taking the last Distance object in the array,
     * and comparing it with each element from the top down
     */
    private void siftDown(){
        int half = size / 2;                // the last row is at least 2x all the previous nodes
        int i = 0;
        Distance d = array[size];           // grab the last element, which is now 1 past "end" of array
        while(i < half){                    // if i == half, i has no children; i < half must have children
            int child = (2*i) + 1;
            int rChild = child + 1;
            Distance test = array[child];
            if(rChild < size &&                                     // if right child exists...
                    array[child].compareTo(array[rChild]) > 0) {    // ...and it's bigger than left child...
                test = array[rChild];                               // ...test against right child...
                child = rChild;                                     // ...and update path for next child (if needed)
            }
            if(d.compareTo(test) <=0){                              // if d is smaller or same size as largest child...
                break;                                              // ...break...
            }
            array[i] = test;                            //...otherwise, move largest child into parent position
            i = child;                                  //...and keep moving down
        }
        array[i] = d;                                   // save d at it's right position
    }

    /**
     * Check if the array is empty
     * This could occur after it has been polled many times
     * @return a boolean if size has reached 0
     */
    public boolean isEmpty(){
        return size == 0;
    }

}
