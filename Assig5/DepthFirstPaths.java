//Simran Gidwani
//CS 1501 Summer 2018
package assig5;

//DepthFirstPaths.java


//Below is the syntax highlighted version of DepthFirstPaths.java from §4.1 Undirected Graphs.


/******************************************************************************
 *  Compilation:  javac DepthFirstPaths.java
 *  Execution:    java DepthFirstPaths G s
 *  Dependencies: Graph.java Stack.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  %  java Graph tinyCG.txt
 *  6 8
 *  0: 2 1 5 
 *  1: 0 2 
 *  2: 0 1 3 4 
 *  3: 5 4 2 
 *  4: 3 2 
 *  5: 3 0 
 *
 *  % java DepthFirstPaths tinyCG.txt 0
 *  0 to 0:  0
 *  0 to 1:  0-2-1
 *  0 to 2:  0-2
 *  0 to 3:  0-2-3
 *  0 to 4:  0-2-3-4
 *  0 to 5:  0-2-3-5
 *
 ******************************************************************************/

/**
 *  The {@code DepthFirstPaths} class represents a data type for finding
 *  paths from a source vertex <em>s</em> to every other vertex
 *  in an undirected graph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Each call to {@link #hasPathTo(int)} takes constant time;
 *  each call to {@link #pathTo(int)} takes time proportional to the length
 *  of the path.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>   
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DepthFirstPaths {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstPaths(EdgeWeightedDigraph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // depth first search from v
    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        for (DirectedEdge w : G.adj(v)) {
            if (!marked[w.to()]) {
                edgeTo[w.to()] = v;
                dfs(G, w.to());
            }
        }
    }

    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returns a path between the source vertex {@code s} and vertex {@code v}, or
     * {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a path between the source vertex
     *         {@code s} and vertex {@code v}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
}
    /**
     * Unit tests the {@code DepthFirstPaths} data type.
     *
     * @param args the command-line arguments
//     */
//    public static void main(String[] args) {
//        In in = new In(args[0]);
//        Graph G = new Graph(in);
//        int s = Integer.parseInt(args[1]);
//        DepthFirstPaths dfs = new DepthFirstPaths(G, s);
//
//        for (int v = 0; v < G.V(); v++) {
//            if (dfs.hasPathTo(v)) {
//                StdOut.printf("%d to %d:  ", s, v);
//                for (int x : dfs.pathTo(v)) {
//                    if (x == s) StdOut.print(x);
//                    else        StdOut.print("-" + x);
//                }
//                StdOut.println();
//            }
//
//            else {
//                StdOut.printf("%d to %d:  not connected\n", s, v);
//            }
//
//        }
//    }
//
//}

