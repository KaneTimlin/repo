import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class TownGraphManager implements TownGraphManagerInterface {

    Graph graph;

    public TownGraphManager() {
        graph = new Graph();
    }

    /**
     * Adds a road with 2 towns and a road name
     *
     * @param town1    name of town 1 (lastname, firstname)
     * @param town2    name of town 2 (lastname, firstname)
     * @param weight   weight of road (length)
     * @param roadName name of road
     * @return true if the road was added successfully
     */
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        Town sourceTown = new Town(town1);
        Town destinationTown = new Town(town2);
        graph.addEdge(sourceTown, destinationTown, weight, roadName);
        return true;
    }

    /**
     * Returns the name of the road that both towns are connected through
     *
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return name of road if town 1 and town2 are in the same road, returns null if not
     */
    @Override
    public String getRoad(String town1, String town2) {
        Town sourceTown = new Town(town1);
        Town destinationTown = new Town(town2);
        return graph.getEdge(sourceTown, destinationTown).toString();
    }

    /**
     * Adds a town to the graph
     *
     * @param name the town's name  (lastname, firstname)
     * @return true if the town was successfully added, false if not
     */
    @Override
    public boolean addTown(String name) {
        return graph.addVertex(new Town(name));
    }

    /**
     * Gets a town with a given name
     *
     * @param name the town's name
     * @return the Town specified by the name, or null if town does not exist
     */
    @Override
    public Town getTown(String name) {
        Town toGet = new Town(name);
        Set<Town> vertexSet = graph.vertexSet();
        for (Town town : vertexSet) {
            if (town.equals(toGet)) {
                return town;
            }
        }
        return null;
    }

    /**
     * Determines if a town is already in the graph
     *
     * @param name the town's name
     * @return true if the town is in the graph, false if not
     */
    @Override
    public boolean containsTown(String name) {
        return graph.containsVertex(new Town(name));
    }

    /**
     * Determines if a road is in the graph
     *
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return true if the road is in the graph, false if not
     */
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        return graph.containsEdge(new Town(town1), new Town(town2));
    }

    /**
     * Creates an arraylist of all road titles in sorted order by road name
     *
     * @return an arraylist of all road titles in sorted order by road name
     */
    @Override
    public ArrayList<String> allRoads() {
        ArrayList<String> output = new ArrayList<>();
        Set<Road> edgeSet = graph.edgeSet();
        for (Road road : edgeSet) {
            output.add(road.toString());
        }
        Collections.sort(output);
        return output;
    }

    /**
     * Deletes a road from the graph
     *
     * @param town1    name of town 1 (lastname, firstname)
     * @param town2    name of town 2 (lastname, firstname)
     * @param roadName the road name
     * @return true if the road was successfully deleted, false if not
     */
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String roadName) {
        Town sourceTown = new Town(town1);
        Town destinationTown = new Town(town2);

        Road newRoad = graph.removeEdge(sourceTown, destinationTown, 1, roadName);
        return newRoad != null;
    }

    /**
     * Deletes a town from the graph
     *
     * @param name name of town (lastname, firstname)
     * @return true if the town was successfully deleted, false if not
     */
    @Override
    public boolean deleteTown(String name) {
        return graph.removeVertex(new Town(name));
    }

    /**
     * Creates an arraylist of all towns in alphabetical order (last name, first name)
     *
     * @return an arraylist of all towns in alphabetical order (last name, first name)
     */
    @Override
    public ArrayList<String> allTowns() {
        ArrayList<String> output = new ArrayList<>();
        Set<Town> vertexSet = graph.vertexSet();
        for (Town town : vertexSet) {
            output.add(town.toString());
        }
        Collections.sort(output);
        return output;
    }

    /**
     * Returns the shortest path from town 1 to town 2
     *
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return an Arraylist of roads connecting the two towns together, null if the
     * towns have no path to connect them.
     */
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        Town startTown = new Town(town1);
        Town destTown = new Town(town2);
        return graph.shortestPath(startTown, destTown);
    }

    /**
     * Populates the graph with the towns and roads listed in the txt file
     * @param selectedFile the file to populate graph with
     * @throws IOException if the file cannot be found
     */
    public void populateTownGraph(File selectedFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
        String line;
        String[] cities;
        String[] road;
        while((line = reader.readLine()) != null) {
            cities = line.split(";");
            road = cities[0].split(",");
            Town town1 = new Town(cities[1]);
            Town town2 = new Town(cities[2]);
            graph.addVertex(town1);
            graph.addVertex(town2);
            graph.addEdge(town1, town2, Integer.parseInt(road[1]), road[0]);
        }

    }
}
