//Simran Gidwani
//CS 1501 Summer 2018
package assig5;

/*************************************************************************
 *  Compilation:  javac DirectedEdge.java
 *  Execution:    java DirectedEdge
 *
 *  Immutable weighted directed edge.
 *
 *************************************************************************/

/**
 *  The <tt>DirectedEdge</tt> class represents a weighted edge in an directed graph.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */

public class DirectedEdge { 
    private final int v;
    private final int w;
    private final int weight;
    private final double weight2;

   /**
     * Create a directed edge from v to w with given weight.
     */
    public DirectedEdge(int v, int w, int weight, double weight2) {
        this.v = v;
        this.w = w;
        this.weight = weight;
        this.weight2 = weight2;
     
    }

   /**
     * Return the vertex where this edge begins.
     */
    public int from() {
        return v;
    }

   /**
     * Return the vertex where this edge ends.
     */
    public int to() {
        return w;
    }

   /**
     * Return the weight of this edge.
     */
    public int weight() 
    { 
        return weight; 
    }
    
    public double weight2()
    {
        return weight2;
    }

   /**
     * Return a string representation of this edge.
     */
    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", weight) + String.format("%5.2f", weight2);
    }
    
    public int other(int vertex)
    {
        if (vertex == v) 
            return w;
        else if (vertex == w) 
            return v;
        else
            throw new IllegalArgumentException("Not an endpoint");
    }

   /**
     * Test client.
     */
//    public static void main(String[] args) {
//        DirectedEdge e = new DirectedEdge(12, 23, 3.14);
//        StdOut.println(e);
//    }
}
