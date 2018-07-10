//Simran Gidwani
//CS 1501 Summer 2018
import java.io.*;
import java.util.*;


public class TSTmod<Value> {
    private static final int R= 256;  // size
    private Node root;   // root of TST

    private static class Node {        
        private Object val;              // value associated with string
        private Node[] codes = new Node[R];
    }

   /**************************************************************
    * Is string key in the symbol table?
    **************************************************************/
    public boolean contains(StringBuilder key) {
        return get(key) != null;
    }

    public Value get(StringBuilder key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    // return subtrie corresponding to given key
    private Node get(Node x, StringBuilder key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.codes[c], key, d+1);
    }


   /**************************************************************
    * Insert key&value into the symbol table.
    **************************************************************/
    public void put(StringBuilder key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, StringBuilder key, Value val, int d) {
        if (x == null)
            x = new Node();
        if (d == key.length())
        {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.codes[c] = put(x.codes[c], key, val, d+1);
        return x;
    }


   /**************************************************************
    * Find and return longest prefix of s in TST
    **************************************************************/
    public StringBuilder longestPrefixOf(StringBuilder s) {
        int prefix = longestPrefixOf(root, s, 0, 0);
        return new StringBuilder(s.substring(0, prefix));    
    }

    private int longestPrefixOf(Node x, StringBuilder s, int d, int l)
    {
        if (x == null) 
            return l;
        if (x.val != null)
            l = d;
        if (d == s.length())
            return l;
        char c = s.charAt(d);
        return longestPrefixOf(x.codes[c], s, d+1, l);
       
    }
    // all keys in symbol table
    public Iterable<StringBuilder> keys() {
        return prefixMatch(null);
    }

    // all keys starting with given prefix
    public Iterable<StringBuilder> prefixMatch(StringBuilder prefix) {
        Queue<StringBuilder> queue = new Queue<StringBuilder>();
        Node x = get(root, prefix, 0);
        collect(x, prefix, queue);
        return queue;
    }
    
    // return all keys matching given wilcard pattern
    public Iterable<StringBuilder> wildcardMatch(StringBuilder pat) {
        Queue<StringBuilder> queue = new Queue<StringBuilder>();
        collect(root, null, pat, queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, StringBuilder prefix, Queue<StringBuilder> queue) {
        if (x == null) return;
        if (x.val != null) queue.enqueue(prefix);
        for (int i = 0; i < R; i++)
        {
            collect(x.codes[i], prefix.append((char)i), queue);
        }
    }
 
    public void collect(Node x, StringBuilder prefix, StringBuilder pat, Queue<StringBuilder> q) {
        if (x == null) return;
        if (prefix.length() == pat.length() && x.val != null)
        {
            q.enqueue(prefix);
        }
        if (prefix.length() == pat.length())
            return;
        char c = pat.charAt(prefix.length());
        for (int i = 0; i < R; i++)
        {
            if (c == '.' || c == i)
            {
                collect(x.codes[c], prefix.append((char)i), pat, q);
            }
        }
        
    }
    
    public void delete(StringBuilder key)
    {
        root = delete(root, key, 0);
    }
    
    public Node delete(Node x, StringBuilder key, int d)
    {
        if (x == null)
            return null;
        if (d == key.length())
            x.val = null;
        else
        {
            char c = key.charAt(d);
            x.codes[c] = delete(x.codes[c], key, d+1);
        }
        if (x.val != null)
            return x;
        for (int i = 0; i < R; i++)
        {
            if (x.codes[i] != null)
                return x;
        }
        return null;
    }
//
//    // test client
//    public static void main(String[] args) {
//        // build symbol table from standard input
//        TST<Integer> st = new TST<Integer>();
//        for (int i = 0; !StdIn.isEmpty(); i++) {
//            String key = StdIn.readString();
//            st.put(key, i);
//        }
//
//
//        // print results
//        for (String key : st.keys()) {
//            StdOut.println(key + " " + st.get(key));
//        }
//    }
}

