//Simran Gidwani
//CS 1501 Summer 2018

//main class
package assig5;
import java.util.*;
import java.io.*;

/**
 *
 * @author simrangidwani
 */
public class Assig5 {
    
    public static String [] cities;
    public static EdgeWeightedDigraph graph;
    public static String [] routeArray;
    public static double [] pathPrices;

    /**
     * Main method- takes in no commands from the command line and handles 
     * the menu driven loop.
     * This method begins by reading in the input file, splitting each 
     * line by city 1, city2, distance and the cost and adding the edges going
     * to and from each city to the graph. It then displays a menu allowing the
     * user to chose which option he or she would like to view. Using a 
     * switch-case, I adapted each case to handle the inquiries in individual ways.
     * Each case calls to a different method that returns the information the
     * user is asking for. Continues displaying the cases until they chose to 
     * quit and save.
     * @param args command line arguments-in this case none
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int choice;
        String fileName;
        int numCities;
        Scanner inScan = new Scanner(System.in);
        System.out.println("Enter File Name: ");
        fileName = inScan.nextLine();
        File f = new File(fileName);
        inScan = new Scanner(f);
        numCities = Integer.parseInt(inScan.next()) + 1;
        graph = new EdgeWeightedDigraph(numCities);   
        cities = new String[numCities];
        
        
        for (int i = 0; i < cities.length; i++)
        {
            cities[i] = inScan.nextLine();           
        }
        while (inScan.hasNext())
        {
            String route = inScan.nextLine();
            routeArray = route.split(" ");
            int city1 = Integer.parseInt(routeArray[0]);
            int city2 = Integer.parseInt(routeArray[1]);
            int distance = Integer.parseInt(routeArray[2]);
            double cost = Double.parseDouble(routeArray[3]);
            //add both ways making the return flights also paths
            graph.addEdge(new DirectedEdge(city1, city2, distance, cost));
            graph.addEdge(new DirectedEdge(city2, city1, distance, cost));
        }
        
    inScan = new Scanner(System.in);
    while (true)
    {
        //Menu for options:
        System.out.println("Welcome! Please choose an option: \n"
                + "1) Show entire list of direct routes, distance and prices\n"
                + "2) Display Minimum Spanning Tree\n"
                + "3) Shortest path based on MILES\n"
                + "4) Shortest path based on PRICE\n"
                + "5) Shortest path based on # of HOPS\n"
                + "6) Display all trips under a certain price\n"
                + "7) Add a new route\n"
                + "8) Remove a route\n"
                + "9) Quit & Save");
        choice = inScan.nextInt();      
        
        switch(choice)
        {
            //edit case1
            case 1:
                System.out.println("Showing list of routes...");
                System.out.println();
                printRoutes();               
                break;
                
            case 2:
                System.out.println("Showing Minimum Spanning Tree...");
                System.out.println("--------------------------------");
                mst();
                break;
                
            case 3:
                for (int i = 0; i < cities.length; i++)
                {
                    System.out.println(cities[i]);
                }
                System.out.print("Enter departure city: ");
                String city1 = inScan.next();
                System.out.print("Enter arriving city: ");
                String city2 = inScan.next();
                System.out.println();
                System.out.println("Showing shortest path based on MILES...");
                System.out.println("---------------------------------------");               
                shortMiles(city1, city2);
                break;
              
                //HERE
            case 4:
                for (int i= 0; i < cities.length; i++)
                {
                    System.out.println(cities[i]);
                }
                System.out.print("Enter departure city: ");
                city1 = inScan.next();
                System.out.print("Enter arriving city: ");
                city2 = inScan.next();
                System.out.println();
                System.out.println("Showing shortest path based on PRICE...");
                System.out.println("---------------------------------------");
                shortPrice(city1, city2);
                break;
                
            case 5:
                for (int i= 0; i < cities.length; i++)
                {
                    System.out.println(cities[i]);
                }
                System.out.print("Enter departure city: ");
                city1 = inScan.next();
                int begin = getValue(city1);
                System.out.print("Enter arriving city: ");
                city2 = inScan.next();            
                int end = getValue(city2);
                System.out.println();              
                System.out.println("Showing shortest path based on HOPS...");
                System.out.println("------------------------------------------");
                shortHops(begin, end);
                break;
                
            case 6:
                System.out.println("Enter max price you would like to spend: ");
                int maxPrice = Integer.parseInt(inScan.next());
                System.out.println("Showing routes less or equal to " + maxPrice + " ...");
                routesLess(maxPrice);
                break;
                
            case 7:
                System.out.print("Enter Departure City: ");
                city1 = inScan.next();
                System.out.print("Enter Arriving City: ");
                city2 = inScan.next();
                int distance;
                System.out.print("Enter distance: ");
                distance = Integer.parseInt(inScan.next());
                double cost;
                System.out.print("Enter cost: ");
                cost = Double.parseDouble(inScan.next());
                
                System.out.println("Adding route from " + city1 + " to " + city2 + "...");
                System.out.println("-----------------------------------------------------");
                addFlight(city1, city2, distance, cost);                
                break;
                
            case 8:
                for (int i = 0; i < cities.length; i++)
                {
                    System.out.println(cities[i]);
                }
                System.out.println("Enter route to remove: ");
                System.out.println("City 1: ");
                String rC1 = inScan.next();
                int removeC1 = getValue(rC1);
                System.out.println("City 2: ");
                String rC2 = inScan.next();
                int removeC2 = getValue(rC2);
                
                System.out.println("Removing route from " + rC1 + " to " + rC2 + "...");
                System.out.println("-------------------------------------------------------------");
                remove(removeC1, removeC2);
                break;
                
            case 9:
                System.out.println("Saving changes and exiting program...");
                save(fileName);
   
                
        }
        System.out.println();
        System.out.println("Returning to main menu...");
        System.out.println();
        
    }
   
}

    public static void printRoutes()
    {
        for (int i = 0; i < cities.length; i++)
        {
            Iterator<DirectedEdge> edges = graph.adj(i).iterator();
            if (edges == null) continue;
            while (edges.hasNext())
            {
                DirectedEdge edge = edges.next();
                int city1 = edge.from();
                int city2 = edge.to();
                int distance = (int) edge.weight();
                double price = edge.weight2();
                System.out.println("From " + cities[city1] + " to " + cities[city2] +"- Distance: " + distance + " Cost: " + price);
            }
        }
    }
/**
 * Method GetValue. Indexes through the arraylist of cities to get the integer 
 * value at which it is stored.
 * @param cityName the city for which you are indexing
 * @return the index at which that city is located in the arraylist.
 */
    public static int getValue(String cityName)
    {
        for (int i = 1; i < cities.length+1; i++)
        {
            if (cityName.equals(cities[i]))
            {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Creates a prim object that iterates through all the edges and returns
     * the minimum spanning tree.
     */
    public static void mst()
    {
        PrimMST prim = new PrimMST(graph);
        Iterator<DirectedEdge> e = prim.edges().iterator();
        while (e.hasNext())
        {
            DirectedEdge edge = e.next();
            System.out.println(cities[edge.to()] + "," + cities[edge.from()] + " : " + edge.weight());
        }
    }
    
    /**
     * Displays the shortest route depending on distance.
     * Using an edgeweighted graph, creates an object of DijkstraSP and uses that
     * algorithm to iterate through the edges of the graph finding the minimum 
     * spanning tree based on the weight of the distance. 
     * @param cit1 the departing city
     * @param cit2 the arriving city
     */
    public static void shortMiles(String cit1, String cit2)
    {
        int c1 = getValue(cit1);
        int c2 = getValue(cit2);
        DijkstraSP minSP = new DijkstraSP(graph, c1);
        Iterator<DirectedEdge> nodes = minSP.pathTo(c2).iterator();
        System.out.println("Shortest path from " + cit1 + " to " + cit2 + " is " + nodes.next().weight());
        System.out.println("Path with edges (in reverse order): \n");
        String print = "";
        while (nodes.hasNext())
        {
            DirectedEdge e = nodes.next();
            print = cities[e.to()] + " " + e.weight() + " " + print;
        }
        print += cities[c1];
        System.out.println(print);
        
    }
    
    
    public static void shortPrice(String cit1, String cit2)
    {
        int c1 = getValue(cit1);
        int c2 = getValue(cit2);
        DijkstraSP minSP = new DijkstraSP(graph, c1, 1);
        Iterator<DirectedEdge> nodes = minSP.pathTo2(c2).iterator();
        System.out.println("Cheapest flight from " + cit1 + " to " + cit2 + " is " + nodes.next().weight());
        System.out.println("Path with edges (in reverse order): \n");
        String print = "";
        while (nodes.hasNext())
        {
            DirectedEdge e = nodes.next();
            print = cities[e.to()] + " " + e.weight2() + " " + print;
        }
        print+= cities[c1];
        System.out.println(print);
        
    }
    
    public static void shortHops(int start, int finish)
    {
        BreadthFirstPaths bfs = new BreadthFirstPaths(graph, start);
        Iterable<Integer> path = bfs.pathTo(finish);
        String [] paths = path.toString().split(" ");
        System.out.println("Fewest Hops from " + cities[start] + " to " + cities[finish] + " is: " + (paths.length -1));
        System.out.println("Path with edges (in reverse order): \n");
        for (int i = paths.length-1; i>=0; i--)
        {
            System.out.print(cities[Integer.parseInt(paths[i])] + " ");
        }
	System.out.println();
        
    }

    /**Calls three other methods.
     * After going through this method and creating an ArrayList for the various
     * routes that exist in the graph, it calls the allPaths method that returns
     * a recursive call to pathsRec which essentially uses source and destination
     * parameters passed in from allPaths at each call that it finds a route with
     * a price less than the one indicated. It iterates through each of the paths
     * possible recursively adding the next city and checking the total while 
     * backtracking to remove that city if the price goes over the indicated amount.
     * 
     * 
     * @param cost entered by the user representing the highest cost they 
     * would be willing to pay for their flight.
     */
    public static void routesLess(int cost){
	int counter =0;
        ArrayList<String> route = new ArrayList<>();
        for(int i =1; i<cities.length; i++) {
            for (int x = 1; x < cities.length; x++) {
                if (x == i) {
                    x++;
                    if (x == cities.length) {
                       break;
                    }
                }
            ArrayList<ArrayList<Integer>> allPaths = allPaths(i,x);
            for(int a =0; a<allPaths.size(); a++){
                Iterator<Integer> path = allPaths.get(a).iterator();
                int price =0;
                int city1 = path.next();
                int city2 = path.next();
                String output = cities[city1];
                while(true){
                    Iterator<DirectedEdge> e = graph.adj(city1).iterator();
                    while(e.hasNext()){
                        DirectedEdge edge = e.next();
                        if(edge.to() == city2){
                            price += edge.weight2();
                            output += " " + (int)edge.weight2() + " " + cities[city2];
                        }
                    }
                    if(!path.hasNext())
                        break;
                    city1 = city2;
                    city2 = path.next();
                }
                if(price<=cost){

                    String out = "Cost: " + price + " Path (reversed): " + output;
                    if(!route.contains(out)) {
                        System.out.println(out);
                        counter++;
                        route.add(out);
                    }
                }
            }
            }
        }
    }
    public static ArrayList<ArrayList<Integer>> allPaths(int source, int destination){
        ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
        pathsRec(source, destination, paths, new LinkedHashSet<Integer>());
        return paths;
	}
	private static void pathsRec(int c, int d, ArrayList<ArrayList<Integer>> paths, LinkedHashSet<Integer> path){
		path.add(c);

        if (c == d) {
            paths.add(new ArrayList<Integer>(path));
            path.remove(c);
            return;
        }

        ArrayList<Integer> edges = new ArrayList<>();
        Iterator<DirectedEdge> e = graph.adj(c).iterator();
        while(e.hasNext()){
            edges.add(e.next().to());
        }

        for (int t : edges) {
            if (!path.contains(t)) {
                pathsRec(t, d, paths, path);
            }
        }
        path.remove(c);
    }	
    
        /** Adds a route.
         * Creates new edges in the graph based on what the user enters as the
         * departing and arriving cities. Because it's an undirected graph
         * makes sure that the flights are going in both directions.
         * 
         * @param c1 departing city
         * @param c2 arriving city
         * @param d  the distance
         * @param p  the price
         */
    public static void addFlight(String c1, String c2, int d, double p)
    {
        int city1 = getValue(c1);
        int city2 = getValue(c2);
        graph.addEdge(new DirectedEdge(city1, city2, d, p));
        graph.addEdge(new DirectedEdge(city2, city1, d, p));
    }
    
    /**
     * Removes path.
     * removes a user specified path from the graph and adjusts the remaining
     * edges to fill that spot.
     * @param c1 the departing city
     * @param c2 the arriving city
     */
    public static void remove(int c1, int c2)
    {
        Iterator<DirectedEdge> edges = graph.adj[c1].iterator();
        Stack<DirectedEdge> s = new Stack<>();
        while(edges.hasNext()){
            DirectedEdge e = edges.next();
            if(e.to() == c2){
                continue;
            }
            s.push(e);
        }
        graph.adj[c1] = new Bag<>();
        while(!s.isEmpty()){
            graph.adj[c1].add(s.pop());
        }

        edges = graph.adj[c2].iterator();
        s = new Stack<>();


        while(edges.hasNext()){
            DirectedEdge e = edges.next();
            if(e.to() == c1){
                continue;
            }
            s.push(e);
        }
        graph.adj[c2] = new Bag<>();
        while(!s.isEmpty()){
            graph.adj[c2].add(s.pop());
        }
    }
    
    /**
     * Saves file back to inputfile.
     * Essentially uses a filewriter to write back to the file that was originally
     * passed in to read from. Using the data that might have been changed as in 
     * adding a route or removing a route, 
     * 
     * @param filename the name of the file that is specified by the user
     * @throws IOException 
     */
    public static void save(String filename) throws IOException
    {
        File f = new File(filename);
        ArrayList<DirectedEdge> copies = new ArrayList<>();
        FileWriter writer = new FileWriter(f);
        writer.write((cities.length - 1) + "\n");
        for(int i = 1; i<cities.length; i++){
            writer.write(cities[i] + "\n");
        }
        for(int i=1; i<cities.length;i++ ) {
            Iterator<DirectedEdge> edges = graph.adj(i).iterator();
            if (edges == null) continue;
            while (edges.hasNext()) {
                DirectedEdge edge = edges.next();
                int city1 = edge.from();
                int city2 = edge.to();
                int distance = (int) edge.weight();
                int price =(int) edge.weight2();
                if(copies.size() == 0){
                    writer.write(city1 + " "+ city2 + " " + distance + " " + price + "\n"  );
                }
                for(int x = 0; x<copies.size(); x++){
                    if(copies.get(x).to() == city1 && copies.get(x).from() == city2){
                        break;
                    }
                    if(x == copies.size()-1){
                        writer.write(city1 + " "+ city2 + " " + distance + " " + price + "\n");
                    }
                }
                copies.add(edge);
            }
        }
        writer.close();
        System.exit(0);
    }
    
}
