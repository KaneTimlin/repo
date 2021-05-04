import org.jetbrains.annotations.NotNull;
import org.junit.internal.InexactComparisonCriteria;

import java.util.*;

/**
 * @author Kane Timlin
 */
public class Graph implements GraphInterface<Town, Road>{

    private final int INITIAL_SIZE = 53;

    private Town[] towns;
    private LinkedList<Road>[] roads;

    PriorityQueue<Integer> queue;
    int[] weights;
    Town[] previousTowns;

    public Graph() {
        towns = new Town[INITIAL_SIZE];
        roads = new LinkedList[INITIAL_SIZE];

        weights = new int[INITIAL_SIZE];
        previousTowns = new Town[INITIAL_SIZE];

        queue = new PriorityQueue<>(11, new CompareByWeight());

    }

    /**
     * Returns an edge connecting source vertex to target vertex if such
     * vertices and such edge exist in this graph. Otherwise returns
     * null. If any of the specified vertices is null
     * returns null
     * <p>
     * In undirected graphs, the returned edge may have its source and target
     * vertices in the opposite order.
     *
     * @param sourceVertex      source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @return an edge connecting source vertex to target vertex.
     */
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {
        int index = getTownIndex(sourceVertex);
        if (index == -1 || index == -2 || destinationVertex == null || destinationVertex.isEmpty()) {
            return null;
        }
        LinkedList<Road> roadsOfTown = roads[index];
        for (Road road : roadsOfTown) {
            if (road.getDestination().equals(destinationVertex)) {
                return new Road(road);
            }
        }
        return null;
    }

    /**
     * Creates a new edge in this graph, going from the source vertex to the
     * target vertex, and returns the created edge.
     * <p>
     * The source and target vertices must already be contained in this
     * graph. If they are not found in graph IllegalArgumentException is
     * thrown.
     *
     * @param sourceVertex      source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight            weight of the edge
     * @param description       description for edge
     * @return The newly created edge if added to the graph, otherwise null.
     * @throws IllegalArgumentException if source or target vertices are not
     *                                  found in the graph.
     * @throws NullPointerException     if any of the specified vertices is null.
     */
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        int index = getTownIndex(sourceVertex);
        if (index == -1 || destinationVertex == null || destinationVertex.isEmpty()) {
            throw new NullPointerException();
        }
        if (index == -2) {
            throw new IllegalArgumentException();
        }
        Road newRoad = new Road(sourceVertex, destinationVertex, weight, description);
        Town adjacentTown = towns[index];
        adjacentTown.addAdjacentTown(destinationVertex);

        LinkedList<Road> roadsOfTown = roads[index];
        if (roadsOfTown == null) {
            roadsOfTown = new LinkedList<>();
            roads[index] = roadsOfTown;
        }
        roadsOfTown.add(newRoad);

        index = getTownIndex(destinationVertex);
        if (index == -1 || index == -2) {
            return null;
        }
        roadsOfTown = roads[index];
        newRoad = new Road(destinationVertex, sourceVertex, weight, description);
        adjacentTown = towns[index];
        adjacentTown.addAdjacentTown(sourceVertex);
        if (roadsOfTown == null) {
            roadsOfTown = new LinkedList<>();
            roads[index] = roadsOfTown;
        }
        roadsOfTown.add(newRoad);

        return newRoad;
    }

    /**
     * Adds the specified vertex to this graph if not already present. More
     * formally, adds the specified vertex, v, to this graph if
     * this graph contains no vertex u such that
     * u.equals(v). If this graph already contains such vertex, the call
     * leaves this graph unchanged and returns false. In combination
     * with the restriction on constructors, this ensures that graphs never
     * contain duplicate vertices.
     *
     * @param town vertex to be added to this graph.
     * @return true if this graph did not already contain the specified
     * vertex.
     * @throws NullPointerException if the specified vertex is null.
     */
    @Override
    public boolean addVertex(Town town) {
        int index = getNewTownIndex(town);
        if (index == -1) {
            throw new NullPointerException();
        } else if (index == -2) {
            return false;
        }
        towns[index] = town;
        return true;

    }

    /**
     * Returns true if and only if this graph contains an edge going
     * from the source vertex to the target vertex. In undirected graphs the
     * same result is obtained when source and target are inverted. If any of
     * the specified vertices does not exist in the graph, or if is
     * null, returns false.
     *
     * @param sourceVertex      source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @return true if this graph contains the specified edge.
     */
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        boolean containsEdge = false;
        int index = getTownIndex(sourceVertex);
        if (index == -1 || index == -2 || destinationVertex == null || destinationVertex.isEmpty()) {
            return false;
        }
        LinkedList<Road> roadsOfTown = roads[index]; // check one way
        for (Road road : roadsOfTown) {
            if (road.getDestination().equals(destinationVertex)) {
                containsEdge = true;
            }
        }

        index = getTownIndex(destinationVertex); // check the other way
        if (index == -2) {
            return false;
        }
        roadsOfTown = roads[index];
        for (Road road : roadsOfTown) {
            if (road.getDestination().equals(sourceVertex)) {
                containsEdge = true;
            }
        }
        return containsEdge;
    }

    /**
     * Returns true if this graph contains the specified vertex. More
     * formally, returns true if and only if this graph contains a
     * vertex u such that u.equals(v). If the
     * specified vertex is null returns false.
     *
     * @param town vertex whose presence in this graph is to be tested.
     * @return true if this graph contains the specified vertex.
     */
    @Override
    public boolean containsVertex(Town town) {
        int index = getTownIndex(town);
        return !(index == -1 || index == -2);
    }

    /**
     * Returns a set of the edges contained in this graph. The set is backed by
     * the graph, so changes to the graph are reflected in the set. If the graph
     * is modified while an iteration over the set is in progress, the results
     * of the iteration are undefined.
     *
     * @return a set of the edges contained in this graph.
     */
    @Override
    public Set<Road> edgeSet() {
        Set<Road> outputSet = new TreeSet<>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            if (towns[i] != null) {
                outputSet.addAll(edgesOf(towns[i]));
            }
        }
        return outputSet;
    }

    /**
     * Returns a set of all edges touching the specified vertex (also
     * referred to as adjacent vertices). If no edges are
     * touching the specified vertex returns an empty set.
     *
     * @param vertex the vertex for which a set of touching edges is to be
     *               returned.
     * @return a set of all edges touching the specified vertex.
     * @throws IllegalArgumentException if vertex is not found in the graph.
     * @throws NullPointerException     if vertex is null.
     */
    @Override
    public Set<Road> edgesOf(Town vertex) {
        int index = getTownIndex(vertex);
        if (index == -1) {
            throw new NullPointerException();
        } else if (index == -2) {
            throw new IllegalArgumentException();
        }
        if (roads[index] == null) {
            return new HashSet<>();
        }
        return new HashSet<>(roads[index]);
    }

    /**
     * Removes an edge going from source vertex to target vertex, if such
     * vertices and such edge exist in this graph.
     * <p>
     * If weight >- 1 it must be checked
     * If description != null, it must be checked
     * <p>
     * Returns the edge if removed
     * or null otherwise.
     *
     * @param sourceVertex      source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight            weight of the edge
     * @param description       description of the edge
     * @return The removed edge, or null if no edge removed.
     */
    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        int index = getTownIndex(sourceVertex);
        if (index == -1 || index == -2 || destinationVertex == null || destinationVertex.isEmpty()) {
            return null;
        }
        LinkedList<Road> listOfRoads = roads[index];
        Road roadToRemove = new Road();
        towns[index].removeAdjacentTown(destinationVertex);

        for (Road road : listOfRoads) {
            if (destinationVertex.equals(road.getDestination())) {
                if (weight > 1 && description != null) {
                    if (weight == road.getWeight() && description.equals(road.getName())) {
                        roadToRemove = road;
                    }
                } else if (weight > 1) {
                    if (weight == road.getWeight()) {
                        roadToRemove = road;
                    }
                } else if (description != null) {
                    if (description.equals(road.getName())) {
                        roadToRemove = road;
                    }
                } else {
                    roadToRemove = road;
                }
                break;
            }
        }
        if (roadToRemove.isEmpty()) {
            return null;
        }
        listOfRoads.remove(roadToRemove);

        index = getTownIndex(destinationVertex);
        if (index == -2) {
            return null;
        }
        listOfRoads = roads[index];
        listOfRoads.remove(roadToRemove);
        towns[index].removeAdjacentTown(sourceVertex);
        return new Road(roadToRemove);
    }

    /**
     * Removes the specified vertex from this graph including all its touching
     * edges if present. More formally, if the graph contains a vertex
     * u such that u.equals(v), the call removes all edges
     * that touch u and then removes u itself. If no
     * such u is found, the call leaves the graph unchanged.
     * Returns true if the graph contained the specified vertex. (The
     * graph will not contain the specified vertex once the call returns).
     * <p>
     * If the specified vertex is null returns false.
     *
     * @param town vertex to be removed from this graph, if present.
     * @return true if the graph contained the specified vertex;
     * false otherwise.
     */
    @Override
    public boolean removeVertex(Town town) {
        int index = getTownIndex(town);
        if (index == -1 || index == -2) {
            return false;
        }
        LinkedList<Road> listOfRoads = new LinkedList<>(roads[index]);
        for (Road road : listOfRoads) {
            removeEdge(road.getSource(), road.getDestination(), road.getWeight(), road.getName());
        }
        towns[index] = new Town();
        return true;
    }

    /**
     * Returns a set of the vertices contained in this graph. The set is backed
     * by the graph, so changes to the graph are reflected in the set. If the
     * graph is modified while an iteration over the set is in progress, the
     * results of the iteration are undefined.
     *
     * @return a set view of the vertices contained in this graph.
     */
    @Override
    public Set<Town> vertexSet() {
        HashSet<Town> townSet = new HashSet<>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            if (towns[i] != null) {
                townSet.add(towns[i]);
            }
        }
        return townSet;
    }

    /**
     * Find the shortest path from the sourceVertex to the destinationVertex
     * call the dijkstraShortestPath with the sourceVertex
     *
     * @param sourceVertex      starting vertex
     * @param destinationVertex ending vertex
     * @return An arraylist of Strings that describe the path from sourceVertex
     * to destinationVertex
     * They will be in the format: startVertex "via" Edge "to" endVertex weight
     * As an example: if finding path from Vertex_1 to Vertex_10, the ArrayList<String>
     * would be in the following format(this is a hypothetical solution):
     * Vertex_1 via Edge_2 to Vertex_3 4 (first string in ArrayList)
     * Vertex_3 via Edge_5 to Vertex_8 2 (second string in ArrayList)
     * Vertex_8 via Edge_9 to Vertex_10 2 (third string in ArrayList)
     */
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        weights = new int[INITIAL_SIZE];
        previousTowns = new Town[INITIAL_SIZE];
        queue.clear();

        dijkstraShortestPath(sourceVertex);

        ArrayList<String> directions = new ArrayList<>();
        int index = getTownIndex(destinationVertex);
        Town currentTown, previousTown;

        do {
            currentTown = towns[index];
            previousTown = previousTowns[index];
            Road roadBetween = getEdge(currentTown, previousTown);
            if (roadBetween == null || roadBetween.isEmpty()) {
                return new ArrayList<>();
            }
            directions.add(previousTown + " via " + roadBetween + " to " + currentTown + " " + roadBetween.getWeight() + " mi");
            currentTown = previousTown;
            index = getTownIndex(currentTown);
        }
        while (!previousTown.equals(sourceVertex));

        ArrayList<String> output = new ArrayList<>();

        for (int i = directions.size() - 1; i >= 0; i--) {
            output.add(directions.get(i));
        }

        return output;
    }

    /**
     * Dijkstra's Shortest Path Method.  Internal structures are built which
     * hold the ability to retrieve the path, shortest distance from the
     * sourceVertex to all the other vertices in the graph, etc.
     *
     * @param sourceVertex the vertex to find shortest path from
     */
    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        int srcIndex = getTownIndex(sourceVertex);
        weights[srcIndex] = 0;
        previousTowns[srcIndex] = sourceVertex;
        queue.add(srcIndex);

        Set<Town> visited = new HashSet<>();
        int numVertices = vertexSet().size();

        Town currentTown;
        Town nextTown;
        Town visitNext;
        int index;
        int nextIndex;
        int visitNextIndex;
        LinkedList<Road> listOfRoads;
        int newWeight;
        int visitNextWeight;

        while (visited.size() != numVertices && !queue.isEmpty()) {
            index = queue.poll();
            currentTown = towns[index];
            listOfRoads = roads[index];
            for (Road road : listOfRoads) {
                nextTown = road.getDestination();
                if (!visited.contains(currentTown)) {
                    nextIndex = getTownIndex(nextTown);
                    newWeight = road.getWeight() + weights[index];
                    if (weights[nextIndex] == 0 || newWeight < weights[nextIndex]) {
                        weights[nextIndex] = newWeight;
                        previousTowns[nextIndex] = currentTown;
                    }
                    queue.add(nextIndex);
                }
            }
            visited.add(currentTown);
        }
    }

    /**
     * A method to get the index of a town that has already been placed into the array
     * Returns -1 if the given town is null or empty, and -2 if the town is not found
     * @param town the town to find
     * @return the index of the town, if it is present
     */
    public int getTownIndex(Town town) {
        if (town == null || town.isEmpty()) {
            return -1;
        }
        int ip = Math.abs(town.hashCode() % INITIAL_SIZE);
        int q = Math.abs(town.hashCode() / INITIAL_SIZE);
        int offset;
        if (q % INITIAL_SIZE != 0) {
            offset = q;
        } else {
            offset = 19;
        }
        while (towns[ip] != null) {
            if (towns[ip].equals(town)) {
                return ip;
            }
            ip = (ip + offset) % INITIAL_SIZE;

        }
        return -2; // return if town not found in array
    }

    /**
     * A method to find an empty index to place a town in.
     * Returns -1 if town is null or empty, -2 if a duplicate is found
     * @param town the town to find a spot for
     * @return the index of the town, if a spot is available
     */
    public int getNewTownIndex(Town town) {
        if (town == null || town.isEmpty()) {
            return -1;
        }
        int ip = Math.abs(town.hashCode() % INITIAL_SIZE);
        int q = Math.abs(town.hashCode() / INITIAL_SIZE);
        int offset;
        if (q % INITIAL_SIZE != 0) {
            offset = q;
        } else {
            offset = 19;
        }
        int index = 0;
        boolean useIndex = false;
        while (towns[ip] != null) {
            if (towns[ip].equals(town)) { // check for duplicates
                return -2;
            }
            if (!useIndex && towns[ip].isEmpty()) { // check for empty spots in the array that are not null
                useIndex = true; // can only be run once
                index = ip;
            }
            ip = (ip + offset) % INITIAL_SIZE;
        }
        return useIndex ? index : ip;
    }

    /**
     * @author Kane Timlin
     * A class that provides a way to compare and sort towns by their relative distance from the starting vertex
     * Used as a comparator in dijkstra's shortest path algorithm
     * to find the index with the shortest distance from the starting vertex
     */
    private class CompareByWeight implements Comparator<Integer>{

        @Override
        public int compare(Integer o1, Integer o2) {
            return Integer.compare(weights[o1], weights[o2]);
        }
    }

}
